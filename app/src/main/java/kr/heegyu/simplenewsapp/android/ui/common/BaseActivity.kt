package kr.heegyu.simplenewsapp.android.ui.common

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.android.AndroidInjection
import kr.heegyu.simplenewsapp.BR
import kr.heegyu.simplenewsapp.android.ui.common.viewmodel.BaseViewModel


abstract class BaseActivity
<T : ViewDataBinding, VM: BaseViewModel>
    : AppCompatActivity() {

    protected val glide: RequestManager by lazy {
        Glide.with(this)
    }
//    protected val factory: SimpleNewsAppFactory by lazy {
//        SimpleNewsFactoryImpl()
//    }

    abstract val layoutId: Int

    abstract val viewModel: VM

    protected val dataBinding: T by lazy {
        DataBindingUtil.setContentView<T>(this, layoutId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        dataBinding.setVariable(BR.vm, viewModel)
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
        viewModel.onDestroy()
    }
}