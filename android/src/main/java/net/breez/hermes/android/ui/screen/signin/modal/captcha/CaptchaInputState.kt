package net.breez.hermes.android.ui.screen.signin.modal.captcha

import net.breez.hermes.android.mvi.redux.State
import net.breez.hermes.domain.model.CaptchaModel

data class CaptchaInputState(
    val captcha: CaptchaModel,
    val isLoading: Boolean = false,
    val captchaValue: String = "",
) : State