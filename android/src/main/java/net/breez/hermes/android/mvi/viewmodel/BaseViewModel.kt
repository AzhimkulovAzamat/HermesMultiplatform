package net.breez.hermes.android.mvi.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import net.breez.hermes.android.mvi.redux.State

abstract class BaseViewModel<S: State>: ViewModel() {

    abstract val state: StateFlow<S>

    open fun onCreate() {
        //intentionally left empty
    }

    open fun onStart() {
        //intentionally left empty
    }

    open fun onResume() {
        //intentionally left empty
    }

    open fun onPause() {
        //intentionally left empty
    }

    open fun onStop() {

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