package kr.heegyu.simplenewsapp.android

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.realm.Realm
import kr.heegyu.simplenewsapp.android.di.DaggerAppComponent
import javax.inject.Inject


class App : Application()
, HasAndroidInjector
{

    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this)
        Realm.init(this)
        DaggerAppComponent.factory().create(this).inject(this)

    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return injector
    }


    companion object {
        var instance: App? = null
            protected set
    }
}