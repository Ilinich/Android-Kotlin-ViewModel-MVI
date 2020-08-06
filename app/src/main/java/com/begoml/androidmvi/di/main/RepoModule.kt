package com.begoml.androidmvi.di.main

import com.begoml.androidmvi.core.NewsRepository
import com.begoml.androidmvi.core.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepoModule {

    @Binds
    @Singleton
    fun provides(impl: NewsRepositoryImpl): NewsRepository

}