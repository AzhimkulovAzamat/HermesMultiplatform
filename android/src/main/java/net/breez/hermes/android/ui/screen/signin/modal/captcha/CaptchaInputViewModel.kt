package net.breez.hermes.android.ui.screen.signin.modal.captcha

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.breez.hermes.android.mvi.redux.Logger
import net.breez.hermes.android.mvi.redux.Store
import net.breez.hermes.android.mvi.viewmodel.BaseViewModel
import net.breez.hermes.domain.model.CaptchaModel

class CaptchaInputViewModel constructor(
    initialCaptcha: CaptchaModel,
    captchaInputReducer: CaptchaInputReducer,
    logger: Logger
) : BaseViewModel<CaptchaInputState, CaptchaInputAction>() {

    private val store =
        Store(
            CaptchaInputState(captchaValue = "", captcha = initialCaptcha),
            reducer = captchaInputReducer,
            logger = logger
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