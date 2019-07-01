package kr.heegyu.simplenewsapp.android.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsAPI {

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("q") query: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("pageSize")pageSize: Int
    ): Call<NewsResponse>

}