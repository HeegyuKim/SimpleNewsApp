package kr.heegyu.simplenewsapp.android.ui.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.BaseObservable
import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*


open class BaseViewModel : BaseObservable() {

    private val compositeDisposable = CompositeDisposable()
    private val bundleDelegatesMap = TreeMap<String, DelegatedBundle<*>>()

    // lifecycle methods
    open fun onDestroy() {
        clearTasks()
    }

    // live data, observable
    fun <T> mutableLiveData(value: T?): MutableLiveData<T> {
        val liveData = MutableLiveData<T>()
        liveData.value = value
        return liveData
    }

    fun <T : Disposable> T.register(): T {
        compositeDisposable.add(this)
        return this
    }

    fun clearTasks() {
        compositeDisposable.clear()
    }


    // bundle
    protected fun <T> bundle(key: String, defaultValue: T): DelegatedBundle<T> {
        val del = DelegatedBundle(key, defaultValue, this)
        bundleDelegatesMap[key] = del
        return del
    }

    fun restoreState(bundle: Bundle) {
        bundleDelegatesMap.forEach { (k, v) ->
            v.load(bundle)
        }
    }

    fun saveState(bundle: Bundle) {
        bundleDelegatesMap.forEach { (k, v) ->
            v.save(bundle)
        }
    }
}
