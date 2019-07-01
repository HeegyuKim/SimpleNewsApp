package kr.heegyu.simplenewsapp.android.ui.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import kr.heegyu.simplenewsapp.R
import kr.heegyu.simplenewsapp.app.entity.News


class NewsAdapter(
    val proxy: Proxy,
    val glide: RequestManager
)
    : RecyclerView.Adapter<NewsViewHolder>()
, View.OnClickListener
{

    interface Proxy {
        fun addFavorite(news: News)
        fun removeFavorite(news: News)
        fun notifyClicked(news: News)
    }

    val newsList = ArrayList<News>()

    override fun getItemCount() = newsList.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): NewsViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_news, viewGroup, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(vh: NewsViewHolder, position: Int) {
        val news = newsList[position]

        vh.layout.setOnClickListener(this)
        vh.layout.tag = news

        vh.titleView.text = news.title
        vh.contentView.text = news.content
        vh.favoriteButton.tag = news
        vh.favoriteButton.setOnClickListener(this)
        vh.favoriteButton.setImageResource(
            if(news.isFavorite) R.drawable.ic_favorite_black_24dp
            else R.drawable.ic_favorite_border_black_24dp
        )

        glide.load(news.imageUrl)
            .apply(RequestOptions()
                .error(R.drawable.ic_error_outline_black_24dp)
                .placeholder(R.drawable.ic_image_black_24dp)
                .centerInside()
            )
            .into(vh.imageView)
    }


    override fun onClick(view: View) {
        val news = view.tag as News
        when(view.id) {
            R.id.news_layout -> {
                proxy.notifyClicked(news)
            }
            R.id.news_favorite -> {
                if(news.isFavorite)
                    proxy.removeFavorite(news)
                else
                    proxy.addFavorite(news)

                news.isFavorite = !news.isFavorite
                notifyItemChanged(newsList.indexOf(news))
            }
        }
    }
}