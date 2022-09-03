package com.library.android.task.ui.booksearch

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.library.android.task.data.response.Books
import com.library.android.task.data.response.SearchResult
import com.qawafel.android.task.R
import com.qawafel.android.task.databinding.ItemBookBinding

class BooksListAdapter(
    private val context: Context,
    private val onClickListener: OnClickListener,
    private var booksList: List<Books>? = arrayListOf(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun submitList(playerList: List<Books>) {
        this.booksList = playerList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    class OnClickListener(val clickListener: (RepoResponseItem: Books) -> Unit) {
        fun onClick(RepoResponseItem: Books) = clickListener(RepoResponseItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        booksList?.get(position)
            ?.let {
                (holder as ViewHolder).bind(
                    context = context,
                    bookValue = it,
                    onClickListener
                )
            }
    }

    override fun getItemCount(): Int {
        return booksList?.size ?: 0
    }

    private class ViewHolder(val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(
            context: Context,
            bookValue: Books,
            onClickListener: OnClickListener,
        ) {
            val book = bookValue
            if (book != null) {

                binding.tvTitle.text = book.title
                binding.tvAuthor.text = book.authorNames[0] ?: ""

                Glide.with(binding.tvTitle.context)
                    .applyDefaultRequestOptions(
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .error(context.getDrawable(R.drawable.ic_close))
                    ).load(book.coverUrl)
                    .into(binding.ivBookCover)

                binding.rootLayout.setOnClickListener {
                    if (book != null) {
                        onClickListener.clickListener(book)
                    }
                }
            }
        }
    }
}