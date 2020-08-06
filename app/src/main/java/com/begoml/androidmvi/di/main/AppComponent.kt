package com.begoml.androidmvi.di.main

import android.content.Context
import com.begoml.androidmvi.MviApplication
import com.begoml.androidmvi.tools.InitComponentException
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [RepoModule::class]
)
@Singleton
interface AppComponent : AppProvider {

    fun inject(where: MviApplication)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }

    companion object {

        private var component: AppComponent? = null

        fun init(context: Context) {
            component ?: DaggerAppComponent.builder()
                .context(context)
                .build().apply {
                    component = this
                }
        }

        fun get(): AppComponent {
            return component ?: throw InitComponentException()
        }
    }
}