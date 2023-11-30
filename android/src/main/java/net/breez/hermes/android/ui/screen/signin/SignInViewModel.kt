package net.breez.hermes.android.ui.screen.signin

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.breez.hermes.android.middleware.RegistrationCommand
import net.breez.hermes.android.middleware.RegistrationEvent
import net.breez.hermes.android.middleware.RegistrationMiddleWare
import net.breez.hermes.android.mvi.redux.Command
import net.breez.hermes.android.mvi.redux.Event
import net.breez.hermes.android.mvi.redux.LoggerImpl
import net.breez.hermes.android.mvi.redux.MiddlewareContainer
import net.breez.hermes.android.mvi.redux.PostProcessorContainer
import net.breez.hermes.android.mvi.redux.StateAction
import net.breez.hermes.android.mvi.redux.Store
import net.breez.hermes.android.mvi.viewmodel.BaseViewModel

class SignInViewModel constructor(
    private val registrationMiddleWare: RegistrationMiddleWare,
    private val phoneInputPostProcessor: PhoneInputPostProcessor,
    phoneInputBootstrapper: PhoneInputBootstrapper,
    reducer: PhoneInputReducer
): BaseViewModel<PhoneInputState>() {

    private val signInMiddlewares = object : MiddlewareContainer {
        override suspend fun process(command: Command): Event {
            return when (command) {
                is RegistrationCommand -> registrationMiddleWare.process(command)
                else -> defaultEvent()
            }
        }
    }

    private val signInPostProcessor = object : PostProcessorContainer {
        override suspend fun process(event: Event): StateAction? {
            return when (event) {
                is RegistrationEvent -> phoneInputPostProcessor.process(event)
                else -> defaultAction()
            }
        }
    }

    private val store = Store(
        initialState = PhoneInputState(),
        reducer = reducer,
        logger = LoggerImpl(),
        bootstrapper = phoneInputBootstrapper,
        middlewares = signInMiddlewares,
        postProcessors = signInPostProcessor
    )

    override val state = store.state
    val viewEffect = store.viewEffect

    override fun onCreate() {
        super.onCreate()
        viewModelScope.launch {
            store.loadData()
        }
    }

    fun sendAction(action: PhoneInputAction) {
        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}