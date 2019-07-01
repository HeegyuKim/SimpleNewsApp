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
import kr.heegyu.simplenewsapp.android.ui.common.NewsAdapter
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import java.util.concurrent.TimeUnit
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import kr.heegyu.simplenewsapp.android.ui.common.LastItemScrollListener
import java.io.InterruptedIOException
import java.lang.Exception


class SearchActivity : BaseActivity(), TextWatcher, View.OnClickListener, NewsAdapter.Proxy
, LastItemScrollListener.Proxy
{

    val repository: NewsRepository by lazy {
        factory.createNewsRepository()
    }

    var page = 0
    var pageSize = 20
    var isLastPage = false
    var searchQuery: String = ""
    var searchJob: Disposable? = null
    var loadNextPageJob: Disposable? = null

    val newsAdapter: NewsAdapter by lazy { NewsAdapter(this, glide) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView.let {
            it.adapter = newsAdapter
            it.layoutManager = LinearLayoutManager(this)
            it.addOnScrollListener(LastItemScrollListener(this))
        }
        edit_search.addTextChangedListener(this)
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
//                val intent = Intent(this, Favo)
//                startActivity(intent)
            }
        }
    }


    override fun addFavorite(news: News) {
        repository.addNews(news)
    }

    override fun removeFavorite(news: News) {
        repository.deleteNews(news.url)
    }

    override fun notifyClicked(news: News) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
        startActivity(intent)
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
                 val news = repository.search(text, "business", page + 1, pageSize)
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

        if(text.isEmpty()) {
            progress.visibility = View.GONE
            return
        }

        progress.visibility = View.VISIBLE

        val job = Single.create<List<News>> { emitter ->
            try {
                val news = repository.search(text, "business", 1, pageSize)
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
