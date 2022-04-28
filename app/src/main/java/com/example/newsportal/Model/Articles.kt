package com.example.newsportal.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Articles {
    @SerializedName("source")
    @Expose
    val source : Source? = null

    @SerializedName("author")
    @Expose
    val author = ""

    @SerializedName("title")
    @Expose
    val title = ""

    @SerializedName("description")
    @Expose
    val description = ""

    @SerializedName("url")
    @Expose
    val url = ""

    @SerializedName("urlToImage")
    @Expose
    val urlToImage = ""

    @SerializedName("publishedAt")
    @Expose
    val publishedAt = ""
}