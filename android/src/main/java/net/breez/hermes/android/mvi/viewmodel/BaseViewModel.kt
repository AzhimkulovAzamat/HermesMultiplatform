package net.breez.hermes.android.mvi.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.breez.hermes.android.mvi.redux.OneShotEvent
import net.breez.hermes.android.mvi.redux.State
import net.breez.hermes.android.mvi.redux.StateAction
import net.breez.hermes.android.mvi.redux.States
import net.breez.hermes.android.mvi.redux.Store

abstract class BaseViewModel<S: State, A: StateAction>: ViewModel() {

    abstract val state: StateFlow<States<S>>
    abstract val viewEffect: SharedFlow<OneShotEvent?>

    abstract fun provideStore(): Store<S, A>

    open fun onCreate() {
        viewModelScope.launch {
            provideStore().viewCreated()
        }
    }

    open fun onStart() {
        //intentionally left empty
    }

    open fun onResume() {
        viewModelScope.launch {
            provideStore().viewResumed()
        }
    }

    open fun onPause() {
        //intentionally left empty
    }

    open fun onStop() {
        //intentionally left empty
    }

    open fun onDestroy() {
        //intentionally left empty
    }

    fun subscribeLifecycle(event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_CREATE -> onCreate()
            Lifecycle.Event.ON_START -> onStart()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_PAUSE -> onPause()
            Lifecycle.Event.ON_STOP -> onStop()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
            Lifecycle.Event.ON_ANY -> {}
        }
    }
}