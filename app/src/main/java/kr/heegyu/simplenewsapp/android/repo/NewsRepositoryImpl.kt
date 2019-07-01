package kr.heegyu.simplenewsapp.android.repo

import io.realm.Realm
import kr.heegyu.simplenewsapp.android.realm.NewsModel
import kr.heegyu.simplenewsapp.android.retrofit.NewsAPI
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import java.util.*


class NewsRepositoryImpl(
    val newsAPI: NewsAPI
) : NewsRepository {

    val country = "kr"


    override fun search(query: String, category: String, page: Int, pageSize: Int): List<News> {
        val response = newsAPI.getTopHeadlines(query, country, category, page, pageSize).execute()
        return if(response.isSuccessful) {
            response.body()?.articles?.map {
                News(
                    it.url ?: "",
                    it.title ?: "",
                    Date(), // it.publishedAt,
                    it.content ?: "",
                    it.urlToImage ?: "",
                    isFavorites(it.url)
                )
            } ?: emptyList()
        }
        else emptyList()
    }

    override fun addNews(news: News) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.insertOrUpdate(NewsModel(news))
        }
        realm.close()
    }

    override fun updateNews(news: News) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.insertOrUpdate(NewsModel(news))
        }
        realm.close()
    }

    override fun deleteNews(url: String) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.where(NewsModel::class.java)
                .equalTo("url", url)
                .findAll()
                .deleteAllFromRealm()
        }
        realm.close()
    }

    override fun getFavorites(page: Int, pageSize: Int): List<News> {
        val realm = Realm.getDefaultInstance()
        val favorites = realm.where(NewsModel::class.java)
            .findAll()
            .map { it.toNews() }
        realm.close()
        return favorites
    }

    override fun isFavorites(url: String): Boolean {
        val realm = Realm.getDefaultInstance()
        val favorites = realm.where(NewsModel::class.java)
            .equalTo("url", url)
            .findFirst() != null
        realm.close()
        return favorites
    }

    override fun close() {

    }
}