package com.ogaihto.news.services

import com.ogaihto.news.entities.NewsResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsService {
    @Headers("Accept: application/json")
    @GET("top-headlines")
    fun searchNews(@Query("q") query: String = "", @Query("country") country: String = "br", @Query("apiKey") key: String = "9c12a92c55df43cf8a8eff1c8e9dab4d"): Call<NewsResult>
}