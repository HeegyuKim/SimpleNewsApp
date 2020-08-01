package kr.heegyu.simplenewsapp.android.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kr.heegyu.simplenewsapp.android.di.activity.FavoriteActivityModule
import kr.heegyu.simplenewsapp.android.di.activity.SearchActivityModule
import kr.heegyu.simplenewsapp.android.di.scope.ActivityScope
import kr.heegyu.simplenewsapp.android.ui.favorite.FavoriteActivity
import kr.heegyu.simplenewsapp.android.ui.search.SearchActivity

@Module
interface ActivityContributor {

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchActivityModule::class])
    fun provideSearchActivity(): SearchActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [FavoriteActivityModule::class])
    fun provideFavoriteActivity(): FavoriteActivity
}