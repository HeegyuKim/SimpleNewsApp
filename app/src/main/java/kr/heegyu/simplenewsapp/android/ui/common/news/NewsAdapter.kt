package kr.heegyu.simplenewsapp.android.ui.common.news

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.util.Log
import kr.heegyu.simplenewsapp.R
import kr.heegyu.simplenewsapp.android.ui.common.adapter.DataBindingAdapter
import kr.heegyu.simplenewsapp.android.ui.common.viewholder.DataBindingViewHolder
import kr.heegyu.simplenewsapp.app.entity.News
import kr.heegyu.simplenewsapp.app.repo.NewsRepository
import kr.heegyu.simplenewsapp.databinding.ItemNewsBinding
import javax.inject.Inject


class NewsAdapter
@Inject constructor (
    val repo: NewsRepository
)
    : DataBindingAdapter<ItemNewsBinding>(R.layout.item_news)
{
    val newsList = ObservableArrayList<News>()

    init {
        val listChangedCallback = object: ObservableList.OnListChangedCallback<ObservableList<News>>() {
            override fun onChanged(sender: ObservableList<News>?) {
                notifyDataSetChanged()
                Log.d(TAG, "obslist.onChanged()")
            }

            override fun onItemRangeRemoved(
                sender: ObservableList<News>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeRemoved(positionStart, itemCount)
                Log.d(TAG, "obslist.onItemRangeRemoved($positionStart, $itemCount)")
            }

            override fun onItemRangeMoved(
                sender: ObservableList<News>?,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                for(i in 0 until itemCount)
                    notifyItemMoved(fromPosition, toPosition + i)
                Log.d(TAG, "obslist.onItemRangeMoved($fromPosition, $toPosition, $itemCount)")
            }

            override fun onItemRangeInserted(
                sender: ObservableList<News>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeInserted(positionStart, itemCount)
                Log.d(TAG, "obslist.onItemRangeInserted($positionStart, $itemCount)")
            }

            override fun onItemRangeChanged(
                sender: ObservableList<News>?,
                positionStart: Int,
                itemCount: Int
            ) {
                notifyItemRangeChanged(positionStart, itemCount)
                Log.d(TAG, "obslist.onItemRangeChanged($positionStart, $itemCount)")
            }
        }
        newsList.addOnListChangedCallback(listChangedCallback)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: DataBindingViewHolder<ItemNewsBinding>, position: Int) {
        val news = newsList[position]
        holder.binding.vm = NewsViewModel(repo, news)
    }


    companion object {
        val TAG = "NewsAdapter"
    }
}