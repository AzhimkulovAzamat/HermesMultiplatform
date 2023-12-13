package net.breez.hermes.android.ui.screen.signin.modal.captcha

import net.breez.hermes.android.mvi.redux.StateAction
import net.breez.hermes.domain.model.CaptchaModel

sealed class CaptchaInputAction : StateAction {
    data object ApplyCaptcha : CaptchaInputAction()
    data object LoadNewCaptcha : CaptchaInputAction()
    class OnCaptchaInserted(val value: String) : CaptchaInputAction()
    class LoadNewCaptchaSucceed(val element: CaptchaModel) : CaptchaInputAction()
    class LoadNewCaptchaFailed(val throwable: Throwable) : CaptchaInputAction()
}