package com.begoml.androidmvi.view.news

import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.begoml.androidmvi.core.NewsRepository
import javax.inject.Inject

class NewsViewModelFactory @Inject constructor(
    private val repo: NewsRepository,
    fragment: Fragment
) : AbstractSavedStateViewModelFactory(fragment as SavedStateRegistryOwner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return NewsViewModel(handle, repo) as T
    }
}