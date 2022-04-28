package com.example.newsportal.news

import com.example.newsportal.ApiClient
import com.example.newsportal.MainActivity
import com.example.newsportal.Model.Headlines
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// http://newsapi.org/v2/top-headlines?country=in&apiKey=50cc17c391fd4051b486462dd1d1a803
// http://newsapi.org/v2/everything?q=apple&from=2020-08-23&to=2020-08-23&sortBy=popularity&apiKey=50cc17c391fd4051b486462dd1d1a803

interface NewsInterface {
    @GET("top-headlines?apiKey=${MainActivity.API_KEY}")
    fun getHeadlinesNews(
        @Query("country") country:String,
        @Query("page") page:Int
    ): Call<Headlines>
}

object NewsService{
    val newsInstance : NewsInterface
    init {
        val retrofit : Retrofit = Retrofit.Builder().baseUrl(ApiClient.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}



