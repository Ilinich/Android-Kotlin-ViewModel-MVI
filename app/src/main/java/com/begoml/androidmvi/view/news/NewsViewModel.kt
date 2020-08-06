package com.begoml.androidmvi.view.news

import androidx.lifecycle.SavedStateHandle
import com.begoml.androidmvi.core.NewsRepository
import com.begoml.androidmvi.core.model.NewsModel
import com.begoml.androidmvi.mvi.*
import com.begoml.androidmvi.tools.handleResults
import com.begoml.androidmvi.view.news.NewsViewModel.Companion.SAVED_STATE_KEY_NEWS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel(
    savedStateHandle: SavedStateHandle,
    reducer: ReducerImpl,
    actor: ActorImpl,
    postProcessor: PostProcessorImpl,
    bootstrapper: BootstrapperImpl
) : MviViewModel<ViewState, Event, Command, Effect, News>(
    viewState = ViewState(),
    eventToCommandTransformer = UiEventTransformer(),
    actor = actor,
    reducer = reducer,
    postProcessor = postProcessor,
    bootstrapper = bootstrapper
) {

    init {
        savedStateHandle.get<List<NewsModel>>(SAVED_STATE_KEY_NEWS)?.let { value ->
            value.hashCode()
        }
    }

    companion object {
        const val SAVED_STATE_KEY_NEWS = "SAVED_STATE_KEY_NEWS"
    }
}

data class ViewState(
    val isLoading: Boolean = false,
    val newsList: List<NewsModel> = emptyList()
)

class ReducerImpl @Inject constructor() : Reducer<ViewState, Effect> {
    override fun invoke(state: ViewState, effect: Effect): ViewState {
        return when (effect) {
            Effect.StartedLoading -> state.copy(isLoading = true)
            Effect.StoppedLoading -> state.copy(isLoading = false)
            is Effect.NewsLoaded -> state.copy(newsList = effect.newsList)
            else -> state.copy()
        }
    }
}

class ActorImpl @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
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
            Command.SaveInstanceState -> {
                savedStateHandle.set(SAVED_STATE_KEY_NEWS, state.newsList)
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
                    sendEffect(Effect.StoppedLoading)
                    sendEffect(Effect.NewsLoaded(newsList))
                }
            )
        }
    }
}

class PostProcessorImpl @Inject constructor() : PostProcessor<ViewState, Effect, Command> {
    override fun invoke(state: ViewState, effect: Effect): Command? {
        return when (effect) {
            else -> null
        }
    }
}

class BootstrapperImpl @Inject constructor() : Bootstrapper<Command> {

    override fun invoke(sendCommand: (command: Command) -> Unit) {
        sendCommand(Command.StartLoadData)
    }
}

class UiEventTransformer : EventToCommandTransformer<Event, Command> {
    override fun invoke(event: Event): Command {
        return when (event) {
            Event.SaveInstanceState -> Command.SaveInstanceState
        }
    }
}

sealed class Effect {

    object StartedLoading : Effect()
    object StoppedLoading : Effect()

    data class NewsLoaded(val newsList: List<NewsModel>) : Effect()
}

sealed class Event {

    object SaveInstanceState : Event()
}

sealed class News {

}

sealed class Command {

    object StartLoadData : Command()
    object UpdateData : Command()

    object SaveInstanceState : Command()

}