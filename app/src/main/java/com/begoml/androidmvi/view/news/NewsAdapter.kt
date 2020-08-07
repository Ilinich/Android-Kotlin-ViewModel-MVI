package com.begoml.androidmvi.view.news

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.begoml.androidmvi.R
import com.begoml.androidmvi.core.model.NewsModel
import com.begoml.androidmvi.view.BaseRecyclerAdapter
import com.begoml.androidmvi.view.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news.*

class NewsAdapter(private val listener: (model: NewsModel) -> Unit) : BaseRecyclerAdapter<NewsModel, NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(inflate(R.layout.item_news, parent))
    }

    override fun submitList(newList: List<NewsModel>) {
        val oldList = items
        notify(oldList, newList) { old, new -> old.id == new.id }
        items = newList
    }

    inner class NewsViewHolder(override val containerView: View) :
        BaseViewHolder<NewsModel>(containerView), LayoutContainer {

        override fun bind(data: NewsModel) {
            newsTitle.text = data.title
            newsDesc.text = data.description

            containerView.setOnClickListener { listener(data) }
        }
    }
}

fun <T, VH> RecyclerView.Adapter<VH>.notify(
    old: List<T>,
    new: List<T>,
    compare: (T, T) -> Boolean
) where VH : RecyclerView.ViewHolder {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        /**
         * By default you should compare item's id to be sure that items are identical
         */
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compare(old[oldItemPosition], new[newItemPosition])
        }

        /**
         * To use this comparison properly override [equals] method or use data class
         */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }

        override fun getOldListSize() = old.size

        override fun getNewListSize() = new.size
    })

    diff.dispatchUpdatesTo(this)
}