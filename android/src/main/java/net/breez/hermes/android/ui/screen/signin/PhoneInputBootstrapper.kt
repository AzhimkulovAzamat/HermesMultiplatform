package net.breez.hermes.android.ui.screen.signin

import net.breez.hermes.android.mvi.redux.Bootstrapper
import net.breez.hermes.domain.model.CaptchaModel
import net.breez.hermes.domain.model.base.FlowResult
import net.breez.hermes.domain.repositories.TestRepository

class PhoneInputBootstrapper(
    private val testRepository: TestRepository
) : Bootstrapper<PhoneInputAction> {

    override suspend fun viewCreated(newAction: suspend (PhoneInputAction) -> Unit) {
        val result = testRepository.getCaptcha()
        if (result is FlowResult.Success<CaptchaModel>) {
            newAction(PhoneInputAction.LoadCaptchaSucceed(result.element))
        }
    }
}