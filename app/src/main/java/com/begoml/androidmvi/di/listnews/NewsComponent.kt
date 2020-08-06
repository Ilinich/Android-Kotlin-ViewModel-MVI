package com.begoml.androidmvi.di.listnews

import androidx.fragment.app.Fragment
import com.begoml.androidmvi.di.main.AppProvider
import com.begoml.androidmvi.view.news.NewsFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppProvider::class],
    modules = [NewsModule::class]
)
interface NewsComponent {

    fun inject(screen: NewsFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun screen(screen: Fragment): Builder

        fun build(): NewsComponent
        fun appComponent(appComponent: AppProvider): Builder
    }
}
