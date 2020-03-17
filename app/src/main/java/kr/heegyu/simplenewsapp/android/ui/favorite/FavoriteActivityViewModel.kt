package kr.heegyu.simplenewsapp.android.ui.favorite

import android.util.Log
import kr.heegyu.simplenewsapp.android.ui.common.news.NewsAdapter
import kr.heegyu.simplenewsapp.android.ui.common.viewmodel.BaseViewModel
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import javax.inject.Inject


class FavoriteActivityViewModel @Inject constructor (
    val newsAdapter: NewsAdapter,
    val repository: NewsRepository
)
    : BaseViewModel() {

    init {
        val favorites = repository.getFavorites(1)
        newsAdapter.newsList.addAll(favorites)
        newsAdapter.notifyDataSetChanged()

        Log.d(TAG, "all favorites: " + favorites.joinToString(" "))
    }


    companion object {
        val TAG = "FavoriteActivityVM"
    }
}