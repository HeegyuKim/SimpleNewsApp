package kr.heegyu.simplenewsapp.android.ui.common.binding

import android.support.v7.widget.RecyclerView
import android.view.View


class BindingViewHolder<BINDING> (
    itemView: View,
    val binding: BINDING
)
    : RecyclerView.ViewHolder(itemView)