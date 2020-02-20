package kr.heegyu.simplenewsapp.android.ui.common.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView


@BindingAdapter("adapter")
fun setRecyclerAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}