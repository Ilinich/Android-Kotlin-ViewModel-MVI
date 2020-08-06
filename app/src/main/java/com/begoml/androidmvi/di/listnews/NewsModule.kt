package com.begoml.androidmvi.di.listnews

import androidx.lifecycle.ViewModelProvider
import com.begoml.androidmvi.view.news.NewsViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface NewsModule {

    @Binds
    fun bindsViewModelFactory(factory: NewsViewModelFactory): ViewModelProvider.Factory
}