package kr.heegyu.simplenewsapp.android.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search.*
import kr.heegyu.simplenewsapp.R
import kr.heegyu.simplenewsapp.android.ui.common.BaseActivity
import kr.heegyu.simplenewsapp.android.ui.common.news.NewsAdapter
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import kr.heegyu.simplenewsapp.databinding.ActivityFavoriteBinding
import javax.inject.Inject

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding, FavoriteActivityViewModel>() {

    @Inject
    lateinit var vm: FavoriteActivityViewModel
    override val layoutId: Int = R.layout.activity_favorite
    override val viewModel: FavoriteActivityViewModel
        get() = vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Log.d(TAG, "onCreate() LOL + ${vm.newsAdapter.newsList.joinToString(" ")}")
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        val TAG = "FavoriteActivity"
    }
}
