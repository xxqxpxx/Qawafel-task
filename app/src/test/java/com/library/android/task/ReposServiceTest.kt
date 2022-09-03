package com.library.android.task

import android.util.Log
import com.library.android.task.data.repo.BooksRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject


@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class ReposServiceTest {
    private val TAG = "PlanetaryServiceTest"

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: BooksRepository

    @Before
    fun init() {
        hiltAndroidRule.inject()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testResponse2Code() = runTest {
       var work =  repository.fetchResults("lord+of+" , 1)
           .catch { exception ->
               Log.i(TAG, "Exception : ${exception.message}")
               assert(false)
           }
           .collect { response ->
               Log.i(TAG, "Response : $response")
               assert(response.numFound > 0)
           }
    }


}
