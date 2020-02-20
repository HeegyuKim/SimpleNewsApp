package kr.heegyu.simplenewsapp.android.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import kr.heegyu.simplenewsapp.R
import kr.heegyu.simplenewsapp.android.ui.common.BaseActivity
import kr.heegyu.simplenewsapp.android.ui.common.news.NewsAdapter
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import java.util.concurrent.TimeUnit
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kr.heegyu.simplenewsapp.android.ui.common.LastItemScrollListener
import kr.heegyu.simplenewsapp.android.ui.common.news.NewsAdapterProxyImpl
import kr.heegyu.simplenewsapp.android.ui.favorite.FavoriteActivity
import java.io.InterruptedIOException
import java.lang.Exception
import android.app.Activity
import android.view.inputmethod.InputMethodManager


class SearchActivity : BaseActivity(), TextWatcher, View.OnClickListener
, LastItemScrollListener.Proxy
{
    companion object {
        private val TAG = "SearchActivity"
    }

    val repository: NewsRepository by lazy {
        factory.createNewsRepository()
    }

    var page = 0
    var pageSize = 20
    var isLastPage = false
    var searchQuery: String = ""
    var searchJob: Disposable? = null
    var loadNextPageJob: Disposable? = null

    val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(
            repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView.let {
            it.adapter = newsAdapter
            it.layoutManager = LinearLayoutManager(this)
            it.addOnScrollListener(LastItemScrollListener(this))
        }
        edit_search.addTextChangedListener(this)
        edit_search.setOnEditorActionListener { v, actionId, event ->
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
        btn_favorite.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchJob?.dispose()
        loadNextPageJob?.dispose()
    }

    override fun afterTextChanged(s: Editable?) {
        val text = s?.toString() ?: return
        search(text)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}


    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun getItemCount() = newsAdapter.itemCount
    override fun notifyLastItemShown() {
        loadNextPage()
    }

    fun loadNextPage() {
        if(isLastPage || loadNextPageJob != null)
            return

        loadNextPageJob?.dispose()

        val text = edit_search.text.toString()
        val job = Single.create<List<News>> { emitter ->
             try {
                 val news = repository.search(text, page + 1, pageSize)
                 emitter.onSuccess(news)
            }
             catch (e: InterruptedIOException) {}
            catch (e: Exception) {
                emitter.onError(e)
            }
        }

        loadNextPageJob = job.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { news, e ->
                e?.printStackTrace()
                news?.let {
                    isLastPage = news.isEmpty()
                    if(isLastPage)
                        Log.d(TAG, "No more items..")

                    val count = newsAdapter.newsList.size
                    newsAdapter.newsList.addAll(news)
                    newsAdapter.notifyItemRangeInserted(count, news.size)

                    page ++
                }

                loadNextPageJob = null
            }
    }


    fun search(text: String) {
        searchJob?.dispose()
        searchJob = null
        loadNextPageJob?.dispose()
        loadNextPageJob = null

        if(text.isEmpty()) {
            progress.visibility = View.GONE
            return
        }

        progress.visibility = View.VISIBLE

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

        searchJob = job.delay(2000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { news, e ->
                news?.let {
                    newsAdapter.newsList.clear()
                    newsAdapter.newsList.addAll(news)
                    newsAdapter.notifyDataSetChanged()

                    page = 1
                }

                searchJob = null
                e?.printStackTrace()
                progress.visibility = View.GONE
            }
    }
}
