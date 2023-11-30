package net.breez.hermes.android.ui.screen.signin

import net.breez.hermes.android.mvi.redux.Bootstrapper

class PhoneInputBootstrapper : Bootstrapper<PhoneInputAction> {
    override suspend fun viewDidLoad(newAction: suspend (PhoneInputAction) -> Unit) {
//        when (val result = registrationRepository.getCaptcha()) {
//            is FlowResult.Exception -> PhoneInputAction.FetchingCaptchaFailed(result.throwable)
//            is FlowResult.Success -> PhoneInputAction.FetchingCaptchaSucceeded(result.element)
//        }
    }
}