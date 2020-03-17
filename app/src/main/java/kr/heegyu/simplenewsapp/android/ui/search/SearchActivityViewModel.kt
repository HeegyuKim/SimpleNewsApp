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
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import kr.heegyu.simplenewsapp.R
import kr.heegyu.simplenewsapp.android.ui.common.LastItemScrollListener
import kr.heegyu.simplenewsapp.android.ui.common.news.NewsAdapter
import kr.heegyu.simplenewsapp.android.ui.common.viewmodel.BaseViewModel
import kr.heegyu.simplenewsapp.android.ui.favorite.FavoriteActivity
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import java.io.InterruptedIOException
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject



class SearchActivityViewModel @Inject constructor (
    val activity: SearchActivity,
    val repository: NewsRepository,
    val newsAdapter: NewsAdapter
)
    : BaseViewModel()
    , TextWatcher
    , LastItemScrollListener.Proxy

{
    var query = mutableLiveData("")
    var progress = mutableLiveData(false)
    var page = mutableLiveData(0)
    var pageSize = 20
    var isLastPage = mutableLiveData(false)


    val lastItemScrollListener = LastItemScrollListener(this)



    fun onFavoriteButtonClicked() {
        val intent = Intent(activity, FavoriteActivity::class.java)
        activity.startActivity(intent)
    }

    override fun afterTextChanged(s: Editable?) {
        val text = s?.toString() ?: return
        search(text)
        Log.d(TAG, "search($text)")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun getItemCount() = newsAdapter.itemCount
    override fun notifyLastItemShown() {
        loadNextPage()
    }

    fun loadNextPage() {
        clearTasks()

        val job = Single.create<List<News>> { emitter ->
            try {
                val news = repository.search(query.value ?: "", (page.value ?: -1) + 1, pageSize)
                emitter.onSuccess(news)
            }
            catch (e: InterruptedIOException) {}
            catch (e: Exception) {
                emitter.onError(e)
            }
        }

        job.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { news, e ->
                e?.printStackTrace()
                news?.let {
                    isLastPage.value = news.isEmpty()
                    if(isLastPage.value == true)
                        Log.d(TAG, "No more items..")

                    val count = newsAdapter.newsList.size
                    newsAdapter.newsList.addAll(news)
                    newsAdapter.notifyItemRangeInserted(count, news.size)

                    page.value = (page.value ?: -1) + 1
                }
            }
            .register()
    }


    fun search(text: String) {
        clearTasks()

        progress.value = text.isNotEmpty()
        if(progress.value != true)
            return


        val job = Single.create<List<News>> { emitter ->
            try {
                val news = repository.search(text, 1, pageSize)
                emitter.onSuccess(news)
            }
            catch (e: InterruptedIOException) {}
            catch (e: Exception) {
                e.printStackTrace()
                emitter.onSuccess(emptyList())
            }
        }

        job.delay(2000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { news, e ->
                news?.let {
                    newsAdapter.newsList.clear()
                    newsAdapter.newsList.addAll(news)
                    newsAdapter.notifyDataSetChanged()

                    page.value = 1
                }

                e?.printStackTrace()
                progress.value = false
            }
            .register()
    }


    companion object {
        val TAG = "SearchActivityViewModel"
    }
}