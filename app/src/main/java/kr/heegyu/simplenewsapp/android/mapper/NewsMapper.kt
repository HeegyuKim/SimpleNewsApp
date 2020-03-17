package kr.heegyu.simplenewsapp.android.mapper

import kr.heegyu.simplenewsapp.android.retrofit.NewsArticle
import kr.heegyu.simplenewsapp.android.retrofit.NewsResponse
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import java.util.*


fun NewsArticle.toNews(
    repository: NewsRepository
): News {
    val it = this
    return News(
        it.url ?: "",
        it.title ?: "",
        Date(), // it.publishedAt,
        it.content ?: "",
        it.urlToImage ?: "",
        repository.isFavorites(it.url)
    )
}