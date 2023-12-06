package net.breez.hermes.android.mvi.redux

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

@Suppress("UNCHECKED_CAST")
open class Store<S : State, A : StateAction>(
    initialState: S,
    private val reducer: Reducer<S, A>,
    private val logger: Logger,
    private val bootstrapper: Bootstrapper<A>? = null,
    private val middlewares: MiddlewareContainer? = null,
    private val postProcessors: PostProcessorContainer? = null
) {

    private val _state = MutableStateFlow(States(initialState, initialState))
    private val _viewEffect = MutableSharedFlow<OneShotEvent?>()
    val state: StateFlow<States<S>> = _state
    val viewEffect: SharedFlow<OneShotEvent?> = _viewEffect.asSharedFlow()

    private val currentState: S
        get() = _state.value.current

    suspend fun viewCreated() {
        bootstrapper?.viewCreated {
            dispatch(it)
        }
    }

    suspend fun viewResumed() {
        bootstrapper?.viewResumed {
            dispatch(it)
        }
    }

    suspend fun viewReload() {
        bootstrapper?.viewReload {
            dispatch(it)
        }
    }

    suspend fun dispatch(action: A) {
        logger.log(action)

        val reducerResult = reducer.reduce(currentState, action)
        (reducerResult.state as? State)?.let { castedState ->
            logger.log(castedState)
        }

        _state.value = _state.value.updateState(reducerResult.state)
        reducerResult.oneShotEvents.forEach {
            _viewEffect.emit(it)
        }

        val event = middlewares?.process(reducerResult.command) ?: EmptyEvent()
        logger.log(event)
        val newAction = postProcessors?.process(event)

        (newAction as? A)?.let { dispatch(it) }
    }
}