package kr.heegyu.simplenewsapp.android.ui.common.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import kr.heegyu.simplenewsapp.android.ui.common.news.NewsAdapter
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository


class NewsAdapterProxyImpl(
    val context: Context,
    val repository: NewsRepository
    ) : NewsAdapter.Proxy {

    override fun addFavorite(news: News) {
        news.isFavorite = true
        repository.addNews(news)
    }

    override fun removeFavorite(news: News) {
        news.isFavorite = false
        repository.deleteNews(news.url)
    }

    override fun notifyClicked(news: News) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
        context.startActivity(intent)
    }
}