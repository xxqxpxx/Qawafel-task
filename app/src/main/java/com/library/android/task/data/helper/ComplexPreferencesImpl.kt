package com.library.android.task.data.helper

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.library.android.task.data.response.Books
import com.library.android.task.data.response.SearchResult
import com.library.android.task.network.Constants.PREFERENCE_NAME

class ComplexPreferencesImpl(context: Context) {
    var sharedPreferences: SharedPreferences
    private val gson: Gson = Gson()
    private val editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun saveArrayList(list: java.util.ArrayList<Books>, key: String) {
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(key: String): java.util.ArrayList< Books>? {
        val json: String? = sharedPreferences.getString(key, null)
        val type = object : TypeToken<java.util.ArrayList< Books>>() {}.type
        return gson.fromJson(json, type)
    }


    fun saveQueryString(time: String, key: String) {
        val json: String = gson.toJson(time)
        editor.putString(key, json)
        editor.apply()
    }

    fun getCachedQueryString(key: String): String? {
        val json: String? = sharedPreferences.getString(key, null)
        val type = object : TypeToken<String>() {}.type
        return gson.fromJson(json, type)
    }


}