package kr.heegyu.simplenewsapp.android.ui.common

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import kr.heegyu.simplenewsapp.BR
import kr.heegyu.simplenewsapp.android.ui.common.viewmodel.BaseViewModel


abstract class BaseActivity
<T : ViewDataBinding, VM: BaseViewModel>
    : AppCompatActivity() {

//    protected val glide: RequestManager by lazy {
//        Glide.with(this)
//    }
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
        dataBinding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let(viewModel::restoreState)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let(viewModel::saveState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}