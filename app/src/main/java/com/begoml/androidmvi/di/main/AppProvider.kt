package com.begoml.androidmvi.di.main

import android.content.Context
import com.begoml.androidmvi.core.NewsRepository

interface AppProvider {

    fun provideContext(): Context

    fun provideNewsRepository(): NewsRepository
}