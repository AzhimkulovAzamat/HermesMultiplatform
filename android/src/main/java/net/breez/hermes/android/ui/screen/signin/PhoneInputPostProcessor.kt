package net.breez.hermes.android.ui.screen.signin

import net.breez.hermes.android.middleware.RegistrationEvent
import net.breez.hermes.android.mvi.redux.PostProcessor

class PhoneInputPostProcessor :
    PostProcessor<RegistrationEvent, PhoneInputAction> {
    override fun process(event: RegistrationEvent): PhoneInputAction {
        return when(event) {
            is RegistrationEvent.FailedSignIn -> PhoneInputAction.SignInFailed(event.throwable)
            is RegistrationEvent.SuccessSignIn -> PhoneInputAction.SignInConfirmed(event.profileModel)
        }
    }
}