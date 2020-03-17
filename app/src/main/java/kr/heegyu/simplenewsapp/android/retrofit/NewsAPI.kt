package kr.heegyu.simplenewsapp.android.retrofit

import io.reactivex.Observable
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
    ): Observable<NewsResponse>

    @GET("everything")
    fun getEverything(
        @Query("q") query: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize")pageSize: Int
    ): Observable<NewsResponse>

}