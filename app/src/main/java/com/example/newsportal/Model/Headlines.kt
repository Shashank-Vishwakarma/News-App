package com.example.newsportal.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Headlines {
    @SerializedName("status")
    @Expose
    val status = ""

    @SerializedName("totalResults")
    @Expose
    val totalResults = ""

    @SerializedName("articles")
    @Expose
    val articles : ArrayList<Articles> = ArrayList()
}