package com.library.android.task.data.repo

import android.util.Log
import com.google.gson.Gson
import com.library.android.task.data.response.SearchResult
import com.library.android.task.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class BooksRepository @Inject constructor(private val apiService: ApiService) {
    private val TAG = "BooksRepository"

    fun fetchResults(q: String, page: Int): Flow<SearchResult> {
        return flow {
            val response = apiService.search(q)

            val myClass: SearchResult = Gson().fromJson(response.body(), SearchResult::class.java)
            Log.i(TAG, "fetchMain response $myClass")
            emit(myClass)
        }
    }


}