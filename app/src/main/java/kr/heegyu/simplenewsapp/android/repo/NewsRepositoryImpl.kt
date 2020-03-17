package kr.heegyu.simplenewsapp.android.repo

import android.util.Log
import io.realm.Realm
import kr.heegyu.simplenewsapp.android.realm.NewsModel
import kr.heegyu.simplenewsapp.android.retrofit.NewsAPI
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import java.util.*
import javax.inject.Inject


class NewsRepositoryImpl
@Inject constructor(
    val newsAPI: NewsAPI
) : NewsRepository
{

    val country = "us"
    val language = "en"


    override fun search(query: String, page: Int, pageSize: Int): List<News> {
        val response = newsAPI.getEverything(query, language, page, pageSize).execute()
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

        Log.d(TAG, "addNews($news)")
    }

    override fun updateNews(news: News) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.insertOrUpdate(NewsModel(news))
        }
        realm.close()
        Log.d(TAG, "updateNews($news)")
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
        Log.d(TAG, "deleteNews($url)")
    }

    override fun getFavorites(page: Int, pageSize: Int): List<News> {
        val realm = Realm.getDefaultInstance()
        val favorites = realm.where(NewsModel::class.java)
            .findAll()
            .map { it.toNews() }
        realm.close()
        return favorites.reversed()
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


    companion object {
        val TAG = "NewsRepositoryImpl"
    }
}