package com.library.android.task.data.repo

import com.library.android.task.data.helper.ComplexPreferencesImpl
import com.library.android.task.data.response.Books
import com.library.android.task.data.response.SearchResult
import com.library.android.task.network.Constants.REPOS_LIST_PREF_NAME

class LocalRepository(private val complexPreferences: ComplexPreferencesImpl) {
    private val TAG = "LocalFavouritePlanetsRepository"

    fun saveFavouriteRepos(planet: Books) {
        val list = getFavouriteReposList()
        list.add(planet)
        complexPreferences.saveArrayList(list, REPOS_LIST_PREF_NAME)
    }

    private fun getFavouriteReposList(): ArrayList<Books> {
        val list = complexPreferences.getArrayList(REPOS_LIST_PREF_NAME)

        return if (list.isNullOrEmpty()) {
            (arrayListOf())
        } else
            list
    }

    fun removeFavouriteRepo(planet: Books) {
        val list = getFavouriteReposList()
        list.remove(planet)
        complexPreferences.saveArrayList(list, REPOS_LIST_PREF_NAME)
    }


}