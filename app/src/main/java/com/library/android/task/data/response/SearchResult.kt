package com.library.android.task.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class SearchResult(
    @field:SerializedName("start") val start: Int,
    @field:SerializedName("num_found") val numFound: Int,
    @field:SerializedName("docs") val books: List<Books>
) : Parcelable


    @Parcelize
    class Books(
        @field:SerializedName("key") val key: String,
        @field:SerializedName("type") val type: String,
        @field:SerializedName("title") val title: String,
        @field:SerializedName("isbn") val isbns: Array<String>,
        @field:SerializedName("cover_edition_key") val coverEditionKey: String? = "",
        @field:SerializedName("author_name") val authorNames: Array<String?>,
        @field:SerializedName("publish_date") val publishDates: Array<String>,
        var coverUrl: String? = "",

    ) : Parcelable
