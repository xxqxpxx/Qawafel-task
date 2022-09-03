package com.library.android.task.ui.bookdetails

import android.content.Intent
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.library.android.task.base.BaseActivity
import com.library.android.task.data.response.Books
import com.library.android.task.data.response.SearchResult
import com.library.android.task.ui.booksearch.BooksLandingActivity
import com.qawafel.android.task.R
import com.qawafel.android.task.databinding.ActivityBookDetailsBinding

class BookDetailsActivity : BaseActivity<ActivityBookDetailsBinding>() {

    private val viewModel: BookDetailsViewModel by viewModels()

    override fun getViewBinding() = ActivityBookDetailsBinding.inflate(layoutInflater)

    var bookItem: Books? = null


    override fun setupView() {
        setupViewModelObservers()
        initListeners()
        getData()
        fillData()
    }


    private fun initListeners() {
        binding.txtTitle.setOnClickListener {
            bookItem?.let { it1 -> goTosearch(it1.title) }
        }

        binding.txtDescription.setOnClickListener {
            goTosearch(bookItem?.authorNames?.get(0) ?: "")
        }
    }

    private fun goTosearch(text: String) {
        val myIntent = Intent(this, BooksLandingActivity::class.java)
        myIntent.putExtra("searchtext", text)
        startActivity(myIntent)
    }


    private fun getData() {
        val value = intent.extras?.get("bookDetails")
        bookItem = value as Books
        viewModel.setData(bookItem)
    }


    private fun fillData() {
        var isbn = ""

        if (bookItem != null) {
            for (item in bookItem?.isbns!!.take(5)) {
                isbn += item + ", "
            }

            binding.txtTitle.text = bookItem?.title
            binding.txtDate.text = bookItem?.publishDates?.get(0) ?: ""
            binding.txtDescription.text = bookItem?.authorNames?.get(0) ?: ""

            binding.txtBookIsbn.text = "ISBNS: $isbn"

            Glide.with(this)
                .applyDefaultRequestOptions(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .error(resources.getDrawable(R.drawable.ic_close))
                )
                .load(bookItem?.coverUrl)
                .into(binding.imgCoverBg)

        }
    }


    override fun setupViewModelObservers() {

    }
}
