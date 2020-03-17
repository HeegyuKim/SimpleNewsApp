package kr.heegyu.simplenewsapp.android.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.databinding.ObservableField
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import kr.heegyu.simplenewsapp.R
import kr.heegyu.simplenewsapp.android.mapper.toNews
import kr.heegyu.simplenewsapp.android.ui.common.LastItemScrollListener
import kr.heegyu.simplenewsapp.android.ui.common.news.NewsAdapter
import kr.heegyu.simplenewsapp.android.ui.common.viewmodel.BaseViewModel
import kr.heegyu.simplenewsapp.android.ui.favorite.FavoriteActivity
import kr.heegyu.simplenewsapp.android.util.ListEvent
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import java.io.InterruptedIOException
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject



class SearchActivityViewModel @Inject constructor (
    val activity: SearchActivity,
    val repository: NewsRepository,
    val newsAdapter: NewsAdapter,
    val newsRelay: Relay<ListEvent<News>>
)
    : BaseViewModel()
    , TextWatcher
    , LastItemScrollListener.Proxy

{
    var query = ""
    var searchDisposable: Disposable? = null
    var progress = mutableLiveData(false)
    var page = mutableLiveData(0)
    var pageSize = 20
    var isLastPage = mutableLiveData(false)


    val lastItemScrollListener = LastItemScrollListener(this)


    init {
        newsRelay.observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                when(event) {
                    is ListEvent.DeleteEvent -> {
                        Log.d(TAG, "relay delete event")
                        event.items.filter { it in newsAdapter.newsList }
                            .forEach {
                                val idx = newsAdapter.newsList.indexOf(it)
                                it.isFavorite = false
                                newsAdapter.newsList[idx] = it
                                newsAdapter.notifyItemChanged(idx)
                                Log.d(TAG, "relay change $idx $it")
                            }
                    }
                }

                newsAdapter.notifyDataSetChanged()
                Log.d(TAG, "news relay event!")
            }.register()
        Log.d(TAG, "NewsRelay hash: ${newsRelay.hashCode()}")
    }


    fun onFavoriteButtonClicked() {
        val intent = Intent(activity, FavoriteActivity::class.java)
        activity.startActivity(intent)
    }

    override fun afterTextChanged(s: Editable?) {
        query = s?.toString() ?: return
        page.value = 0
        search(query)
        Log.d(TAG, "search($query)")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun getItemCount() = newsAdapter.itemCount
    override fun notifyLastItemShown() {
        search(query)
    }

    fun search(text: String) {
        searchDisposable?.dispose()

        page.value?.takeIf { it >= 1 }?.let { return }

        searchDisposable = repository.search(query, (page.value ?: -1) + 1, pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { res ->
                    val news = res.articles.map { it.toNews(repository) }

                    val count = newsAdapter.newsList.size
                    if(page.value == 0)
                        newsAdapter.newsList.clear()
                    newsAdapter.newsList.addAll(news)

                    page.value = (page.value ?: -1) + 1
                    isLastPage.value = news.isEmpty()
                    if(isLastPage.value == true)
                        Log.d(TAG, "No more items..")
                },
                {
                    it.printStackTrace()
                }
            )
    }


    companion object {
        val TAG = "SearchActivityViewModel"
    }
}