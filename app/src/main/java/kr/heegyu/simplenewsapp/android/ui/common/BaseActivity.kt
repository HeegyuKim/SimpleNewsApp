package kr.heegyu.simplenewsapp.android.ui.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.android.AndroidInjection
import kr.heegyu.simplenewsapp.android.SimpleNewsFactoryImpl
import kr.heegyu.simplenewsapp.app.SimpleNewsAppFactory


open class BaseActivity : AppCompatActivity() {

    protected val glide: RequestManager by lazy {
        Glide.with(this)
    }
//    protected val factory: SimpleNewsAppFactory by lazy {
//        SimpleNewsFactoryImpl()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        glide.onStart()
    }

    override fun onStop() {
        super.onStop()
        glide.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}