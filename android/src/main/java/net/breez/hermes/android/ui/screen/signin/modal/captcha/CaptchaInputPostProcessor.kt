package net.breez.hermes.android.ui.screen.signin.modal.captcha

import net.breez.hermes.android.middleware.RegistrationEvent
import net.breez.hermes.android.mvi.redux.PostProcessor

class CaptchaInputPostProcessor : PostProcessor<RegistrationEvent, CaptchaInputAction> {
    override fun process(event: RegistrationEvent): CaptchaInputAction {
        return when (event) {
            is RegistrationEvent.FailedLoadCaptcha -> CaptchaInputAction.LoadNewCaptchaFailed(event.throwable)
            is RegistrationEvent.LoadCaptchaCompleted -> CaptchaInputAction.LoadNewCaptchaSucceed(
                event.element
            )

            is RegistrationEvent.FailedSignIn -> TODO()
            is RegistrationEvent.SuccessSignIn -> TODO()
        }
    }
}

