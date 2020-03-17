package kr.heegyu.simplenewsapp.android.retrofit

import kr.heegyu.simplenewsapp.BuildConfig
import kr.heegyu.simplenewsapp.android.repo.NewsRepositoryImpl
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitProvider {

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

    fun provideRetrofit(): Retrofit {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val http = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(NewsAPIInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(NEWS_API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(http)
            .build()

    }


}