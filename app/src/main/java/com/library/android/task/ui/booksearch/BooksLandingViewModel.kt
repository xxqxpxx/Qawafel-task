package com.library.android.task.ui.booksearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.library.android.task.base.BaseViewModel
import com.library.android.task.data.repo.BooksRepository
import com.library.android.task.data.response.Books
 import com.library.android.task.data.response.SearchResult
import com.library.android.task.network.ResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksLandingViewModel @Inject constructor(private val repository: BooksRepository) :
    BaseViewModel() {


    private val _reposDataObserver = MutableLiveData<ResultModel<List<Books>>>()
    val bookDataObserver: LiveData<ResultModel<List<Books>>> = _reposDataObserver


    lateinit var query: String


    private fun fetchData(query: String) {
        viewModelScope.launch {
            _reposDataObserver.postValue(ResultModel.Loading(isLoading = true))

            repository.fetchResults(query, 1)
                .catch { exception ->
                    _reposDataObserver.value =
                        ResultModel.Failure(code = getStatusCode(throwable = exception))
                    _reposDataObserver.postValue(ResultModel.Loading(isLoading = false))
                }
                .collect { response ->
                    var dataWithCovers = fillCoversUrl(response)
                    _reposDataObserver.postValue(ResultModel.Success(data = dataWithCovers.books))
                }

        }
    }


    private fun fillCoversUrl(searchResult: SearchResult): SearchResult {

        for (item in searchResult.books) {
            item.coverUrl = getCoverUrl(CoverKey.OLID, item.coverEditionKey, CoverSize.M, false)
        }

        return searchResult
    }


    /**
     * Get cover URL for a book.
     *
     * "Book covers can be accessed using Cover ID (internal cover ID), OLID (Open Library ID),
     * ISBN, OCLC, LCCN and other identifiers like librarything and goodreads."
     * If any IP tries to access more that the allowed limit, the service will return "403 Forbidden" status.
     *
     * @param key                Type of key identifier to use.
     * @param value              Value for the chosen key type.
     * @param size               Image size.
     * @param blankInvalidReturn Set to true to get a blank image from URL when no image exist
     * for the request, set to false to return a 404 response.
     * @return URL in String format.
     */
    fun getCoverUrl(
        key: CoverKey?,
        value: String?,
        size: CoverSize?,
        blankInvalidReturn: Boolean
    ): String? {
        val url = StringBuilder("https://covers.openlibrary.org/b/")
        when (key) {
            CoverKey.ISBN -> url.append("isbn")
            CoverKey.OCLC -> url.append("oclc")
            CoverKey.LCCN -> url.append("lccn")
            CoverKey.OLID -> url.append("olid")
            CoverKey.ID -> url.append("id")
            else -> {}
        }
        url.append("/").append(value).append("-")
        when (size) {
            CoverSize.S -> url.append("S")
            CoverSize.M -> url.append("M")
            CoverSize.L -> url.append("L")
            else -> {}
        }
        url.append(".jpg")

        return url.toString()
    }


    enum class CoverKey {
        ISBN, OCLC, LCCN, OLID, ID
    }


    enum class CoverSize {
        S, M, L
    }


    fun refresh() {
        fetchData(query)
    }

    fun searchForBookOrAuthor(text: String) {
        query = text
        fetchData(text)
    }

}