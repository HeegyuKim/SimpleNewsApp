package kr.heegyu.simplenewsapp.android.ui.common.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView


@BindingAdapter("adapter", "onScrollListener", requireAll = false)
fun setRecyclerAdapter(
    view: RecyclerView,
    adapter: RecyclerView.Adapter<*>?,
    onScrollListener: RecyclerView.OnScrollListener?
) {
    view.adapter = adapter
    onScrollListener?.let(view::addOnScrollListener)
}
