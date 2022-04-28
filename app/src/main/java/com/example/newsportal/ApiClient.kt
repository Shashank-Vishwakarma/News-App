package com.example.newsportal

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        var apiClient : ApiClient? = null
        val retrofit : Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

        @Synchronized
        fun getInstance() : ApiClient{
            if(apiClient == null){
                apiClient = ApiClient()
            }
            return apiClient as ApiClient
        }
    }

    fun getApi():ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }

}