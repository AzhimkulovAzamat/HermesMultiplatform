package net.breez.hermes.android.ui.screen.signin.modal.captcha

import net.breez.hermes.android.mvi.redux.StateAction

sealed class CaptchaInputAction: StateAction {
    data object ApplyCaptcha : CaptchaInputAction()
    data object LoadNewCaptcha : CaptchaInputAction()
    class OnCaptchaInserted(val value: String): CaptchaInputAction()

}