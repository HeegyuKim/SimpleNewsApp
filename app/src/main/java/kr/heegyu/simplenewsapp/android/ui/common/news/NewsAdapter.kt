package kr.heegyu.simplenewsapp.android.ui.common.news

import kr.heegyu.simplenewsapp.R
import kr.heegyu.simplenewsapp.android.ui.common.adapter.DataBindingAdapter
import kr.heegyu.simplenewsapp.android.ui.common.viewholder.DataBindingViewHolder
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import kr.heegyu.simplenewsapp.databinding.ItemNewsBinding
import javax.inject.Inject


class NewsAdapter
@Inject constructor (
    val repo: NewsRepository
)
    : DataBindingAdapter<ItemNewsBinding>(R.layout.item_news)
//, View.OnClickListener
{
    val newsList = ArrayList<News>()

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: DataBindingViewHolder<ItemNewsBinding>, position: Int) {
        val news = newsList[position]
        holder.binding.vm = NewsViewModel(repo, news)
    }

}