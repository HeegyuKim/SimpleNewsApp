package kr.heegyu.simplenewsapp.android.ui.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log

class LastItemScrollListener(val proxy: Proxy)
    : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {

    val TAG = "LastItemScrollListener"
    interface Proxy {
        fun getItemCount(): Int
        fun notifyLastItemShown()
    }

    override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
        val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()

        if(lastVisiblePosition >= proxy.getItemCount() - 1) {
            proxy.notifyLastItemShown()
        }
    }

    override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }
}