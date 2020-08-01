package kr.heegyu.simplenewsapp.android.ui.common.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kr.heegyu.simplenewsapp.android.ui.common.viewholder.DataBindingViewHolder


abstract class DataBindingAdapter <T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
)
    : androidx.recyclerview.widget.RecyclerView.Adapter<DataBindingViewHolder<T>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = createDataBinding(inflater, parent, viewType)
        val holder = DataBindingViewHolder(binding)
        binding.lifecycleOwner = holder
        return holder
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        // no - op
    }

    open fun createDataBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): T {
        return DataBindingUtil.inflate(inflater, layoutResId, parent, false)
    }

    override fun onViewAttachedToWindow(holder: DataBindingViewHolder<T>) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: DataBindingViewHolder<T>) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }
}

