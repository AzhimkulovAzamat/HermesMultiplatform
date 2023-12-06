package net.breez.hermes.android.ui.screen.signin.modal.captcha

import net.breez.hermes.android.middleware.RegistrationCommand
import net.breez.hermes.android.mvi.redux.OneShotEvent
import net.breez.hermes.android.mvi.redux.Reducer
import net.breez.hermes.android.mvi.redux.ReducerResult

class CaptchaInputReducer : Reducer<CaptchaInputState, CaptchaInputAction> {
    override fun reduce(
        currentState: CaptchaInputState,
        action: CaptchaInputAction
    ): ReducerResult<CaptchaInputState> {
        return when (action) {
            is CaptchaInputAction.OnCaptchaInserted -> {
                ReducerResult(currentState.copy(captchaValue = action.value))
            }
            CaptchaInputAction.ApplyCaptcha -> {
                ReducerResult(state = currentState, OneShotEvent.FinishScreen)
            }
            CaptchaInputAction.LoadNewCaptcha -> {
                ReducerResult(currentState, command = RegistrationCommand.LoadNewCaptcha)
            }
        }
    }
}