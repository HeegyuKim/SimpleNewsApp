package kr.heegyu.simplenewsapp.android.ui.common.news

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import kr.heegyu.simplenewsapp.R
import kr.heegyu.simplenewsapp.android.ui.common.binding.BindingViewHolder
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import kr.heegyu.simplenewsapp.databinding.ItemNewsBinding


class NewsAdapter(
    val repo: NewsRepository
)
    : RecyclerView.Adapter<BindingViewHolder<ItemNewsBinding>>()
//, View.OnClickListener
{

    interface Proxy {
        fun addFavorite(news: News)
        fun removeFavorite(news: News)
        fun notifyClicked(news: News)
    }

    val newsList = ArrayList<News>()

    override fun getItemCount() = newsList.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): BindingViewHolder<ItemNewsBinding> {
        val inflater = LayoutInflater.from(viewGroup.context)
//        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_news, viewGroup, false)
        val binding = DataBindingUtil.inflate<ItemNewsBinding>(inflater, R.layout.item_news, viewGroup, false)
        return BindingViewHolder(
            binding.root,
            binding
        )
    }

    override fun onBindViewHolder(vh: BindingViewHolder<ItemNewsBinding>, position: Int) {
        val news = newsList[position]
        vh.binding.vm = NewsViewModel(repo, news)

//        vh.layout.setOnClickListener(this)
//        vh.layout.tag = news
//
//        vh.titleView.text = news.title
//        vh.contentView.text = news.content
//        vh.favoriteButton.tag = news
//        vh.favoriteButton.setOnClickListener(this)
//        vh.favoriteButton.setImageResource(
//            if(news.isFavorite) R.drawable.ic_favorite_black_24dp
//            else R.drawable.ic_favorite_border_black_24dp
//        )
//
//        glide.load(news.imageUrl)
//            .apply(RequestOptions()
//                .error(R.drawable.ic_error_outline_black_24dp)
//                .placeholder(R.drawable.ic_image_black_24dp)
//                .centerInside()
//            )
//            .into(vh.imageView)
    }


//    override fun onClick(view: View) {
//        val news = view.tag as News
//        when(view.id) {
//            R.id.news_layout -> {
//                proxy.notifyClicked(news)
//            }
//            R.id.news_favorite -> {
//                if(news.isFavorite)
//                    proxy.removeFavorite(news)
//                else
//                    proxy.addFavorite(news)
//
//                news.isFavorite = !news.isFavorite
//                notifyItemChanged(newsList.indexOf(news))
//            }
//        }
//    }
}