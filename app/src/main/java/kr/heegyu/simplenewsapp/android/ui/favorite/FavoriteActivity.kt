package kr.heegyu.simplenewsapp.android.ui.favorite

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_search.*
import kr.heegyu.simplenewsapp.R
import kr.heegyu.simplenewsapp.android.ui.common.BaseActivity
import kr.heegyu.simplenewsapp.android.ui.common.news.NewsAdapter
import kr.heegyu.simplenewsapp.android.ui.common.news.NewsAdapterProxyImpl
import kr.heegyu.simplenewsapp.app.repo.NewsRepository

class FavoriteActivity : BaseActivity() {


    val repository: NewsRepository by lazy {
        factory.createNewsRepository()
    }
    val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(
            repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView.let {
            it.adapter = newsAdapter
            it.layoutManager = LinearLayoutManager(this)
        }

        newsAdapter.newsList.addAll(repository.getFavorites(1))
        newsAdapter.notifyDataSetChanged()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
