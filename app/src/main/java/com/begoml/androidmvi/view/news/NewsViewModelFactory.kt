package com.begoml.androidmvi.view.news

import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.begoml.androidmvi.core.NewsRepository
import javax.inject.Inject

class NewsViewModelFactory @Inject constructor(
    private val newsRepository: NewsRepository,
    fragment: Fragment,
    private val reducer: ReducerImpl,
    private val postProcessor: PostProcessorImpl,
    private val bootstrapper: BootstrapperImpl
) : AbstractSavedStateViewModelFactory(fragment as SavedStateRegistryOwner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return NewsViewModel(
            savedStateHandle = handle,
            reducer = reducer,
            actor = ActorImpl(
                newsRepository = newsRepository,
                savedStateHandle = handle
            ),
            postProcessor = postProcessor,
            bootstrapper = bootstrapper
        ) as T
    }
}