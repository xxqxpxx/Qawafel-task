package com.library.android.task.network

import com.google.gson.JsonObject
 import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {


    @GET(Constants.BOOKS_SEARCH_URL)
    suspend fun search(
        @Query("q") query: String?
    ): Response<JsonObject>

}