package com.begoml.androidmvi

import android.app.Application
import com.begoml.androidmvi.core.NewsRepository
import com.begoml.androidmvi.di.main.AppComponent
import com.begoml.androidmvi.di.main.AppProvider
import javax.inject.Inject

class MviApplication : Application(), BaseApp {

    @Inject
    lateinit var repo: NewsRepository

    override fun onCreate() {
        super.onCreate()

        AppComponent.init(applicationContext)
        AppComponent.get().inject(this)
    }

    override fun getAppProvider(): AppProvider {
        return AppComponent.get()
    }
}

interface BaseApp {

    fun getAppProvider(): AppProvider
}
