package kr.heegyu.simplenewsapp.android

import android.app.Application
import io.realm.Realm


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        Realm.init(this)
    }


    companion object {
        var instance: App? = null
            protected set
    }
}