package net.breez.hermes.android.ui.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import net.breez.hermes.android.mvi.redux.State
import net.breez.hermes.android.mvi.redux.StateAction
import net.breez.hermes.android.mvi.redux.States
import net.breez.hermes.android.mvi.viewmodel.BaseViewModel

@Composable
fun <S : State, A: StateAction> ComposableLifecycle(
    viewModel: BaseViewModel<S, A>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    content: @Composable (States<S>) -> Unit
) {
    val states by viewModel.state.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            viewModel.subscribeLifecycle(event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    content(states)
}