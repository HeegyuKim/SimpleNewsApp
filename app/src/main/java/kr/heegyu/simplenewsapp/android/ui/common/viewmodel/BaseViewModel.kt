package kr.heegyu.simplenewsapp.android.ui.common.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    fun <T> mutableLiveData(value: T?): MutableLiveData<T> {
        val liveData = MutableLiveData<T>()
        liveData.value = value
        return liveData
    }

    fun <T : Disposable> T.register(): T {
        compositeDisposable.add(this)
        return this
    }

    open fun onDestroy() {
        clearTasks()
    }

    fun clearTasks() {
        compositeDisposable.clear()
    }
}
