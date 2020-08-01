package kr.heegyu.simplenewsapp.android.ui.common.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("adapter", "onScrollListener", requireAll = false)
fun setRecyclerAdapter(
    view: androidx.recyclerview.widget.RecyclerView,
    adapter: androidx.recyclerview.widget.RecyclerView.Adapter<*>?,
    onScrollListener: androidx.recyclerview.widget.RecyclerView.OnScrollListener?
) {
    view.adapter = adapter
    onScrollListener?.let(view::addOnScrollListener)
}
