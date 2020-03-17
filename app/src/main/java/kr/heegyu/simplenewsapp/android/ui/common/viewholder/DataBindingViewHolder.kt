package kr.heegyu.simplenewsapp.android.ui.common.viewholder

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


open class DataBindingViewHolder <T : ViewDataBinding> (
    val binding: T
) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root), LifecycleOwner {

    val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.markState(Lifecycle.State.INITIALIZED)
    }

    fun onAttach() {
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
        lifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }


    fun onDetach() {
        lifecycleRegistry.markState(Lifecycle.State.DESTROYED)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}