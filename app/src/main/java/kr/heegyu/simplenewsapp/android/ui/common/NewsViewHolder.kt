package kr.heegyu.simplenewsapp.android.ui.common

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_news.view.*


class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val layout = itemView.news_layout
    val imageView = itemView.news_image
    val titleView = itemView.news_title
    val contentView = itemView.news_content
    val favoriteButton = itemView.news_favorite
}