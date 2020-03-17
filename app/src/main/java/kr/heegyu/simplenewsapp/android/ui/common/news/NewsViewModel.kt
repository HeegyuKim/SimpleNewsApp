package kr.heegyu.simplenewsapp.android.ui.common.news

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.databinding.ObservableField
import android.net.Uri
import kr.heegyu.simplenewsapp.BR
import kr.heegyu.simplenewsapp.android.App
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import javax.inject.Inject


class NewsViewModel @Inject constructor (
    val repo: NewsRepository,
    newsItem: News
) : ViewModel()
{
    val news = ObservableField<News>(newsItem)


    fun onFavoriteClick() {
        val news = news.get() ?: return

        news.isFavorite = !news.isFavorite
        if(news.isFavorite)
            repo.addNews(news)
        else
            repo.deleteNews(news.url)

        this.news.notifyChange()
    }

    fun onNewsClick() {
        val news = news.get() ?: return
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        App.instance?.startActivity(intent)
    }
}