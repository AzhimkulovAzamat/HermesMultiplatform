package net.breez.hermes.android.ui.screen.signin.modal.captcha

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.breez.hermes.android.middleware.RegistrationCommand
import net.breez.hermes.android.middleware.RegistrationEvent
import net.breez.hermes.android.middleware.RegistrationMiddleWare
import net.breez.hermes.android.mvi.redux.Command
import net.breez.hermes.android.mvi.redux.Event
import net.breez.hermes.android.mvi.redux.Logger
import net.breez.hermes.android.mvi.redux.MiddlewareContainer
import net.breez.hermes.android.mvi.redux.PostProcessorContainer
import net.breez.hermes.android.mvi.redux.StateAction
import net.breez.hermes.android.mvi.redux.Store
import net.breez.hermes.android.mvi.viewmodel.BaseViewModel
import net.breez.hermes.domain.model.CaptchaModel

class CaptchaInputViewModel constructor(
    initialCaptcha: CaptchaModel,
    captchaInputReducer: CaptchaInputReducer,
    logger: Logger,
    captchaInputPostProcessor: CaptchaInputPostProcessor,
    registrationMiddleWare: RegistrationMiddleWare
) : BaseViewModel<CaptchaInputState, CaptchaInputAction>() {

    private val postProcessorContainer = object : PostProcessorContainer {
        override suspend fun process(event: Event): StateAction? {
            return when (event) {
                is RegistrationEvent -> {
                    captchaInputPostProcessor.process(event)
                }

                else -> null
            }
        }
    }

    private val middlewareContainer = object : MiddlewareContainer {
        override suspend fun process(command: Command): Event {
            return when (command) {
               is RegistrationCommand -> { registrationMiddleWare.process(command) }
                else -> defaultEvent()
            }
        }
    }

    private val store =
        Store(
            CaptchaInputState(captchaValue = "", captcha = initialCaptcha),
            reducer = captchaInputReducer,
            logger = logger,
            postProcessors = postProcessorContainer,
            middlewares = middlewareContainer
        )
    override val state = store.state
    override val viewEffect = store.viewEffect
    override fun provideStore(): Store<CaptchaInputState, CaptchaInputAction> = store

    fun sendAction(action: CaptchaInputAction) {
        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}