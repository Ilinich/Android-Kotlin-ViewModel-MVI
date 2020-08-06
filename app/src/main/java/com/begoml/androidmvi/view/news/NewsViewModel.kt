package com.begoml.androidmvi.view.news

import androidx.lifecycle.SavedStateHandle
import com.begoml.androidmvi.core.NewsRepository
import com.begoml.androidmvi.core.model.NewsModel
import com.begoml.androidmvi.mvi.*
import com.begoml.androidmvi.tools.NotImplementedException
import com.begoml.androidmvi.tools.handleResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
) : MviViewModel<ViewState, Event, Command, Effect, News>(
    viewState = ViewState(),
    eventToCommandTransformer = UiEventTransformer(),
    actor = ActorImpl(newsRepository),
    reducer = ReducerImpl(),
    postProcessor = PostProcessorImpl(),
    bootstrapper = BootstrapperImpl()
) {

    init {
        savedStateHandle.get<List<NewsModel>>(KEY_NEWS)?.let { value ->

        }
    }

    companion object {
        private const val KEY_NEWS = "key_news"
    }
}

data class ViewState(
    val isLoading: Boolean = false,
    val newsList: List<NewsModel> = emptyList()
)

class ReducerImpl : Reducer<ViewState, Effect> {
    override fun invoke(state: ViewState, effect: Effect): ViewState {
        return when (effect) {
            Effect.StartedLoading -> state.copy(isLoading = true)
            Effect.StoppedLoading -> state.copy(isLoading = false)
            is Effect.NewsLoaded -> state.copy(newsList = effect.newsList)
            else -> state.copy()
        }
    }
}

class ActorImpl constructor(
    private val newsRepository: NewsRepository
) : Actor<ViewState, Command, Effect> {

    override fun invoke(state: ViewState, command: Command, viewModelScope: CoroutineScope, sendEffect: (effect: Effect) -> Unit) {
        when (command) {
            Command.StartLoadData -> {
                updateData(viewModelScope, sendEffect)
            }

            Command.UpdateData -> {
                updateData(viewModelScope, sendEffect)
            }
        }
    }

    private fun updateData(viewModelScope: CoroutineScope, sendEffect: (effect: Effect) -> Unit) {

        viewModelScope.launch {

            sendEffect(Effect.StartedLoading)

            newsRepository.getNews().handleResults(
                {
                    sendEffect(Effect.StoppedLoading)

                }, { newsList ->

//                    if (Looper.getMainLooper().thread == Thread.currentThread()) {
//                        // Current Thread is Main Thread.
//                    }

                    sendEffect(Effect.StoppedLoading)
                    sendEffect(Effect.NewsLoaded(newsList))
                }
            )
        }
    }
}

class PostProcessorImpl : PostProcessor<ViewState, Effect, Command> {
    override fun invoke(state: ViewState, effect: Effect): Command? {
        return when (effect) {
            else -> null
        }
    }
}

class BootstrapperImpl : Bootstrapper<Command> {

    override fun invoke(sendCommand: (command: Command) -> Unit) {
        sendCommand(Command.StartLoadData)
    }
}

class UiEventTransformer : EventToCommandTransformer<Event, Command> {
    override fun invoke(event: Event): Command {
        throw NotImplementedException()
    }
}

sealed class Effect {

    object StartedLoading : Effect()
    object StoppedLoading : Effect()
    data class NewsLoaded(val newsList: List<NewsModel>) : Effect()
}

sealed class Event {

}

sealed class News {

}

sealed class Command {

    object StartLoadData : Command()
    object UpdateData : Command()

}