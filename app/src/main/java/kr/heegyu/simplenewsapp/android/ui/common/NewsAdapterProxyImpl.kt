package kr.heegyu.simplenewsapp.android.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository


class NewsAdapterProxyImpl(
    val context: Context,
    val repository: NewsRepository
    ) : NewsAdapter.Proxy {

    override fun addFavorite(news: News) {
        repository.addNews(news)
    }

    override fun removeFavorite(news: News) {
        repository.deleteNews(news.url)
    }

    override fun notifyClicked(news: News) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
        context.startActivity(intent)
    }
}