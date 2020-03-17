package kr.heegyu.simplenewsapp.android.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.databinding.BaseObservable
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import kr.heegyu.simplenewsapp.BR
import java.io.Serializable
import java.lang.NullPointerException
import kotlin.reflect.KProperty


class DelegatedBundle<T> (
    protected var key: String,
    protected var value: T,
    protected var vm: BaseViewModel
){
    private var bindingTarget: Int = -1

    operator fun setValue(thisRef: T, p: KProperty<*>, v: T) {
        value = v
        if (bindingTarget == -1) {
            try {
                BR::class.java.fields.first {
                    it.name == p.name
                }.getInt(null).let {
                    bindingTarget = it
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: NoSuchElementException) {
                e.printStackTrace()
            }
        }
        vm.notifyPropertyChanged(bindingTarget)
    }

    operator fun getValue(thisRef: T, property: KProperty<*>): T {
        return value
    }

    fun load(bundle: Bundle) {
        value = bundle[key] as T
    }

    fun save(outState: Bundle) {
        when (val value = this.value) {
            is Int -> outState.putInt(key, value)
            is Short -> outState.putShort(key, value)
            is Long -> outState.putLong(key, value)
            is Float -> outState.putFloat(key, value)
            is Byte -> outState.putByte(key, value)
            is ByteArray -> outState.putByteArray(key, value)
            is Char -> outState.putChar(key, value)
            is CharArray -> outState.putCharArray(key, value)
            is String -> outState.putString(key, value)
            is CharSequence -> outState.putCharSequence(key, value)
//            is Size -> outState.putSize(key, value)
//            is SizeF -> outState.putSizeF(key, value)
            is Bundle -> outState.putBundle(key, value)
//            is IBinder -> outState.putBinder(key, value)
            is Boolean -> outState.putBoolean(key, value)
            is Parcelable -> outState.putParcelable(key, value)
            is Serializable -> outState.putSerializable(key, value)
            else -> throw ClassCastException("Save Instance Fail : $key")
        }
    }
}