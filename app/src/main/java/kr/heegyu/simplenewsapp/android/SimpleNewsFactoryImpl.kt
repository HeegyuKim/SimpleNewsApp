package kr.heegyu.simplenewsapp.android

import kr.heegyu.simplenewsapp.BuildConfig
import kr.heegyu.simplenewsapp.android.repo.NewsRepositoryImpl
import kr.heegyu.simplenewsapp.android.retrofit.NewsAPI
import kr.heegyu.simplenewsapp.app.SimpleNewsAppFactory
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SimpleNewsFactoryImpl
    : SimpleNewsAppFactory {

    companion object {
        private val NEWS_API_URL = "https://newsapi.org/v2/"
        private val NEWS_API_KEY = BuildConfig.NEWS_API_KEY
    }


    // 모든 Request에 API KEY 삽입
    class NewsAPIInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            builder.addHeader("X-API-Key", NEWS_API_KEY)
            return chain.proceed(builder.build())
        }
    }

    val retrofit: Retrofit

    init {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val http = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(NewsAPIInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(NEWS_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(http)
            .build()
    }

    override fun createNewsRepository(): NewsRepository {
        val newsAPI = retrofit.create(NewsAPI::class.java)
        return NewsRepositoryImpl(newsAPI)
    }

    override fun close() {

    }
}