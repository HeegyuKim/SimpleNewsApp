package kr.heegyu.simplenewsapp.android.ui.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

class LastItemScrollListener(val proxy: Proxy)
    : RecyclerView.OnScrollListener() {

    val TAG = "LastItemScrollListener"
    interface Proxy {
        fun getItemCount(): Int
        fun notifyLastItemShown()
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()

        if(lastVisiblePosition >= proxy.getItemCount() - 1) {
            proxy.notifyLastItemShown()
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }
}