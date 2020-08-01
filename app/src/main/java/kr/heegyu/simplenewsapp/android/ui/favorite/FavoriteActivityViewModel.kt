package kr.heegyu.simplenewsapp.android.ui.favorite

import android.util.Log
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import kr.heegyu.simplenewsapp.android.ui.common.news.NewsAdapter
import kr.heegyu.simplenewsapp.android.ui.common.viewmodel.BaseViewModel
import kr.heegyu.simplenewsapp.android.util.ListEvent
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import javax.inject.Inject


class FavoriteActivityViewModel @Inject constructor (
    val newsAdapter: NewsAdapter,
    val repository: NewsRepository,
    val newsRelay: Relay<ListEvent<News>>
) : BaseViewModel() {

    var page: Int = 0

    init {
        refresh()
        newsRelay.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            when(it) {
                is ListEvent.InsertEvent<News> -> {
                    newsAdapter.newsList.addAll(0, it.items.reversed())
                    Log.d(TAG, "InsertEvent")
                }
                is ListEvent.UpdateEvent<News> -> {
                    it.items.map(newsAdapter.newsList::indexOf)
                        .forEach(newsAdapter::notifyItemChanged)
                    Log.d(TAG, "UpdateEvent")
                }
                is ListEvent.DeleteEvent<News> -> {
                    newsAdapter.newsList.removeAll(it.items)
                    Log.d(TAG, "DeleteEvent")
                }
                else -> {
                    Log.d(TAG, "else")
                    refresh()
                }
            }
            newsAdapter.notifyDataSetChanged()
            }.register()
        Log.d(TAG, "NewsRelay hash: ${newsRelay.hashCode()}")
    }

    fun refresh() {
        page = 1
        val favorites = repository.getFavorites(page)
        newsAdapter.newsList.clear()
        newsAdapter.newsList.addAll(favorites)
    }

    companion object {
        val TAG = "FavoriteActivityVM"
    }
}