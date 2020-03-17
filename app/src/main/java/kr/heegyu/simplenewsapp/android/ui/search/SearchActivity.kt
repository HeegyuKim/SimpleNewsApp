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
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import kr.heegyu.simplenewsapp.android.ui.common.LastItemScrollListener
import kr.heegyu.simplenewsapp.android.ui.favorite.FavoriteActivity
import java.io.InterruptedIOException
import java.lang.Exception
import android.app.Activity
import androidx.lifecycle.ViewModel
import android.view.inputmethod.InputMethodManager
import kr.heegyu.simplenewsapp.BR
import kr.heegyu.simplenewsapp.databinding.ActivitySearchBinding
import javax.inject.Inject


class SearchActivity : BaseActivity<ActivitySearchBinding, SearchActivityViewModel>()

{
    companion object {
        private val TAG = "SearchActivity"
    }

    @Inject
    lateinit var vm: SearchActivityViewModel

    override val layoutId: Int = R.layout.activity_search

    override val viewModel: SearchActivityViewModel
        get() = vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
