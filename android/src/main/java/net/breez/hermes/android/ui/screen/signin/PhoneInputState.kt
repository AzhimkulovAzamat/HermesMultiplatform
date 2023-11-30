package net.breez.hermes.android.ui.screen.signin

import com.google.gson.annotations.Expose
import net.breez.hermes.android.model.PhoneNumberMasks
import net.breez.hermes.android.mvi.redux.State
import net.breez.hermes.domain.model.CaptchaModel
import net.breez.hermes.domain.model.TestModel

data class PhoneInputState(
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
    val country: PhoneNumberMasks = PhoneNumberMasks.KG,
    val phoneNumber: String = "",
    @Expose(deserialize = false)
    val password: String = "",
    val showCountrySelectDialog: Boolean = false,
    val showCaptchaInputBottomSheet: Boolean = false,
    val profileModel: TestModel? = null,
    val captchaModel: CaptchaModel? = null
): State
