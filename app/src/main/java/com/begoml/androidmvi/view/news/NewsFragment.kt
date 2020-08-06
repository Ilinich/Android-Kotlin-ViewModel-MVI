package com.begoml.androidmvi.view.news

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.begoml.androidmvi.BaseApp
import com.begoml.androidmvi.R
import com.begoml.androidmvi.di.listnews.DaggerNewsComponent
import com.begoml.androidmvi.di.listnews.NewsComponent
import com.begoml.androidmvi.mvi.ViewStateWatcher
import kotlinx.android.synthetic.main.news_fragment.*
import javax.inject.Inject

class NewsFragment : Fragment(R.layout.news_fragment) {

    private lateinit var newsComponent: NewsComponent

    @Inject
    lateinit var factory: NewsViewModelFactory

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider({ this.viewModelStore }, factory).get(NewsViewModel::class.java)
    }

    private val newsAdapter by lazy { NewsAdapter() }

    private val watcher = initializeViewStateWatcher<ViewState> {

        ViewState::isLoading { isListLoading ->
            progress.visibility(isListLoading)
        }

        ViewState::newsList { newsList ->
            newsAdapter.submitList(newsList)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(ItemDecoration())
            adapter = newsAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.apply {

            viewState.observe(viewLifecycleOwner, Observer { stateView ->
                stateView?.let {
                    watcher.render(it)
                }
            })

            news.observe(viewLifecycleOwner, Observer { event ->
                event?.let {

                }
            })
        }
    }

    override fun onStop() {
        viewModel.apply {
            viewState.removeObservers(viewLifecycleOwner)
            news.removeObservers(viewLifecycleOwner)
        }

        super.onStop()
    }

    private fun injectDependencies() {
        if (!::newsComponent.isInitialized) {
            DaggerNewsComponent.builder()
                .screen(this@NewsFragment)
                .appComponent((context?.applicationContext as BaseApp).getAppProvider())
                .build().apply {
                    newsComponent = this
                }
        }
        newsComponent.inject(this)
    }
}

fun View.visibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

class ItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.apply {
            top = parent.context.resources.getDimensionPixelSize(R.dimen.margin_20)
            right = parent.context.resources.getDimensionPixelSize(R.dimen.margin_8)
            left = parent.context.resources.getDimensionPixelSize(R.dimen.margin_8)
        }
    }
}

inline fun <T> initializeViewStateWatcher(init: ViewStateWatcher.Builder<T>.() -> Unit): ViewStateWatcher<T> =
    ViewStateWatcher.Builder<T>()
        .apply(init)
        .build()