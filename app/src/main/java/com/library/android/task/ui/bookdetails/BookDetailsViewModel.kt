package com.library.android.task.ui.bookdetails

import com.library.android.task.base.BaseViewModel
import com.library.android.task.data.response.Books
import com.library.android.task.data.response.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor() : BaseViewModel() {


    var repo: Books? = null
    var isFavRepo: Boolean = false

    fun setData(repoResponseItem: Books?) {
        repo = repoResponseItem
    }

}

