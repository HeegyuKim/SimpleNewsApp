package kr.heegyu.simplenewsapp.android.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kr.heegyu.simplenewsapp.android.App
import kr.heegyu.simplenewsapp.android.di.module.RepositoryModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    RepositoryModule::class,
    ActivityContributor::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<App> {
        override fun create(@BindsInstance instance: App?): AppComponent
    }


}