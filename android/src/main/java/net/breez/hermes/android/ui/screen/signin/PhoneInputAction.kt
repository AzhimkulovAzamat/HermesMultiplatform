package net.breez.hermes.android.ui.screen.signin

import net.breez.hermes.android.model.PhoneNumberMasks
import net.breez.hermes.android.mvi.redux.StateAction
import net.breez.hermes.domain.model.CaptchaModel
import net.breez.hermes.domain.model.TestModel

sealed class PhoneInputAction : StateAction {
    data object ShowCountryOptions : PhoneInputAction()
    class SelectedCountry(val phoneNumberMasks: PhoneNumberMasks) : PhoneInputAction()
    class PhoneNumberInserted(val actualValue: String) : PhoneInputAction()
    class PasswordInserted(val actualValue: String) : PhoneInputAction()
    data object SubmitPhoneAndPassword : PhoneInputAction()
    class SignInConfirmed(val profileModel: TestModel) : PhoneInputAction()
    class SignInFailed(val throwable: Throwable) : PhoneInputAction()
    class FetchingCaptchaSucceeded(val captchaModel: CaptchaModel): PhoneInputAction()
    class FetchingCaptchaFailed(val throwable: Throwable): PhoneInputAction()
    data object UserCloseBottomSheet: PhoneInputAction()
}