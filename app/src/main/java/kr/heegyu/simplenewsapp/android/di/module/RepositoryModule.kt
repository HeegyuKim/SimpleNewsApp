package kr.heegyu.simplenewsapp.android.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import kr.heegyu.simplenewsapp.android.repo.NewsRepositoryImpl
import kr.heegyu.simplenewsapp.android.retrofit.NewsAPI
import kr.heegyu.simplenewsapp.android.retrofit.RetrofitProvider
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import retrofit2.Retrofit

@Module
object RepositoryModule {

    @Provides
    @JvmStatic
    fun provideRetrofit(): Retrofit {
        return RetrofitProvider().provideRetrofit()
    }

    @Provides
    @JvmStatic
    fun provideNewsAPI(retrofit: Retrofit): NewsAPI {
        return retrofit.create(NewsAPI::class.java)
    }

    @Provides
    @JvmStatic
    fun provideNewsRepository(newsAPI: NewsAPI): NewsRepository = NewsRepositoryImpl(newsAPI)

}