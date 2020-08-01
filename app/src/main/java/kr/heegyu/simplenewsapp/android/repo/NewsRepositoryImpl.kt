package kr.heegyu.simplenewsapp.android.repo

import android.util.Log
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import kr.heegyu.simplenewsapp.android.mapper.toNews
import kr.heegyu.simplenewsapp.android.realm.NewsModel
import kr.heegyu.simplenewsapp.android.retrofit.NewsAPI
import kr.heegyu.simplenewsapp.android.retrofit.NewsArticle
import kr.heegyu.simplenewsapp.android.retrofit.NewsResponse
import kr.heegyu.simplenewsapp.android.util.ListEvent
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import javax.inject.Inject


class NewsRepositoryImpl (
    val newsAPI: NewsAPI,
    val newsRelay: Relay<ListEvent<News>>
) : NewsRepository
{

    val country = "us"
    val language = "en"


    override fun search(query: String, page: Int, pageSize: Int): Observable<NewsResponse> {
        return newsAPI.getEverything(query, language, page, pageSize)
    }

    override fun addNews(news: News) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.insertOrUpdate(NewsModel(news))
        }
        realm.close()

        Log.d(TAG, "addNews($news)")
        newsRelay.accept(ListEvent.InsertEvent(listOf(news)))
    }

    override fun updateNews(news: News) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.insertOrUpdate(NewsModel(news))
        }
        realm.close()
        Log.d(TAG, "updateNews($news)")
        newsRelay.accept(ListEvent.UpdateEvent(listOf(news)))
    }

    override fun deleteNews(url: String) {
        lateinit var news: List<News>
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val newsArticles = realm.where(NewsModel::class.java)
                .equalTo("url", url)
                .findAll()
            news = newsArticles.map { it.toNews() }
            newsArticles.deleteAllFromRealm()
        }
        realm.close()

        Log.d(TAG, "deleteNews($url)")
        newsRelay.accept(ListEvent.DeleteEvent(news))
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
//        val favoriteRelay = PublishRelay.create<ListEvent<News>>()
    }
}