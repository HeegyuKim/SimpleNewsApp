package kr.heegyu.simplenewsapp.android.di.module

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import dagger.Binds
import dagger.Module
import dagger.Provides
import kr.heegyu.simplenewsapp.android.repo.NewsRepositoryImpl
import kr.heegyu.simplenewsapp.android.retrofit.NewsAPI
import kr.heegyu.simplenewsapp.android.retrofit.RetrofitProvider
import kr.heegyu.simplenewsapp.android.util.ListEvent
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitProvider().provideRetrofit()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideNewsAPI(retrofit: Retrofit): NewsAPI {
        return retrofit.create(NewsAPI::class.java)
    }

    val relay = PublishRelay.create<ListEvent<News>>().toSerialized()
    @Provides
    @JvmStatic
    @Singleton
    fun provideNewsEventRelay(): Relay<ListEvent<News>> = relay

    @Provides
    @JvmStatic
    @Singleton
    fun provideNewsRepository(
        newsAPI: NewsAPI,
        relay: Relay<ListEvent<News>>
    ): NewsRepository = NewsRepositoryImpl(
        newsAPI,
        relay
    )


}