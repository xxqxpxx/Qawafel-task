package com.library.android.task.ui.booksearch

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.library.android.task.base.BaseActivity
import com.library.android.task.data.response.Books
import com.library.android.task.data.response.SearchResult
import com.library.android.task.network.ResultModel
import com.library.android.task.ui.bookdetails.BookDetailsActivity
import com.qawafel.android.task.databinding.ActivityBookLandingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BooksLandingActivity : BaseActivity<ActivityBookLandingBinding>() {

    private val viewModel: BooksLandingViewModel by viewModels()

    private lateinit var booksListAdapter: BooksListAdapter

    override fun getViewBinding() = ActivityBookLandingBinding.inflate(layoutInflater)

    var searchvalue: String? = null

    override fun setupView() {
        setupViewModelObservers()
        initListeners()
        fillData()
        getData()
    }

    private fun getData() {
        val value = intent.extras?.getString("searchtext")
        searchvalue = value
        if (!searchvalue.isNullOrEmpty()) {
            (binding.searchLayout.etSearch as TextView).text = searchvalue
            viewModel.searchForBookOrAuthor(searchvalue!!)
        }
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun fillData() {
        initBooksList()
    }

    private fun initListeners() {
        binding.layoutError.button.setOnClickListener {
            viewModel.refresh()
            hideErrorAndRefresh()
        }

        binding.searchLayout.imgSearch.setOnClickListener {
            searchForBooks()
        }

        binding.searchLayout.etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchForBooks()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun searchForBooks() {
        if (!binding.searchLayout.etSearch.text.isNullOrEmpty()) {
            if (binding.searchLayout.etSearch.text.length >= 2) {
                hideKeyboard()
                viewModel.searchForBookOrAuthor(
                    binding.searchLayout.etSearch.text.toString().trim()
                )
            }
        }
    }

    private fun hideErrorAndRefresh() {
        handleError(isError = false)
    }

    private fun handleBookSelection(books: Books?) {
        val myIntent = Intent(this, BookDetailsActivity::class.java)
        myIntent.putExtra("bookDetails", books)
        startActivity(myIntent)
    }

    var bookOnClickListener = (BooksListAdapter.OnClickListener { item ->
        handleBookSelection(item)
    })

    private fun initBooksList() {
        booksListAdapter = BooksListAdapter(
            context = this@BooksLandingActivity,
            onClickListener = bookOnClickListener
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = booksListAdapter
    }

    override fun setupViewModelObservers() {
        viewModel.bookDataObserver.observe(this, bookDataObserver)

    }


    private fun handleProgress(isLoading: Boolean) {
        if (isLoading)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    private fun onFail() {
        handleProgress(isLoading = false)
        handleError(isError = true)
    }

    private fun handleError(isError: Boolean) {
        if (isError) {
            binding.layoutError.layoutError.visibility = View.VISIBLE
        } else {
            binding.layoutError.layoutError.visibility = View.GONE
        }
    }

    private val bookDataObserver = Observer<ResultModel<List<Books>>> { result ->
        lifecycleScope.launch {
            when (result) {
                is ResultModel.Loading -> {
                    handleProgress(isLoading = result.isLoading ?: false)
                } // Loading
                is ResultModel.Success -> {
                    onSuccess(data = result.data)
                } // Success
                is ResultModel.Failure -> {
                    onFail()
                }
            }
        }
    }

    private fun onSuccess(data: List<Books>?) {
        handleProgress(isLoading = false)

            if (data != null || data?.isEmpty() == false){
                Toast.makeText(this , "No result, try different search term" , Toast.LENGTH_LONG).show()
            }else
                booksListAdapter.submitList((data ?: arrayListOf()))

    }
}
