package net.breez.hermes.android.ui.screen.signin

import net.breez.hermes.android.model.SnackbarOptions
import net.breez.hermes.android.model.toSOR
import net.breez.hermes.android.mvi.redux.OneShotEvent
import net.breez.hermes.android.mvi.redux.Reducer
import net.breez.hermes.android.mvi.redux.ReducerResult
import net.breez.hermes.android.mvi.redux.ToastEventData

class PhoneInputReducer : Reducer<PhoneInputState, PhoneInputAction> {
    override fun reduce(
        currentState: PhoneInputState,
        action: PhoneInputAction
    ): ReducerResult<PhoneInputState> {
        return when (action) {
            is PhoneInputAction.ShowCountryOptions -> onUserWantChangedCountry(currentState)
            is PhoneInputAction.SelectedCountry -> onCountrySelected(action, currentState)
            is PhoneInputAction.PhoneNumberInserted -> onPhoneNumberChanged(action, currentState)
            is PhoneInputAction.PasswordInserted -> onPasswordChanged(action, currentState)
            is PhoneInputAction.SubmitPhoneAndPassword -> submitPhoneAndPassword(currentState)
            is PhoneInputAction.SignInConfirmed -> onSignInConfirmed(currentState, action)
            is PhoneInputAction.SignInFailed -> onSignInFailed(currentState, action)
            is PhoneInputAction.OnCaptchaCompleted -> onCaptchaCompleted(currentState, action)
            is PhoneInputAction.LoadCaptchaSucceed -> {
                ReducerResult(currentState.copy(captchaModel = action.element))
            }

            is PhoneInputAction.FetchingCaptchaFailed -> {
                ReducerResult(currentState)
            }

            is PhoneInputAction.FetchingCaptchaSucceeded -> ReducerResult(
                currentState.copy(
                    captchaModel = action.captchaModel
                )
            )
        }
    }

    private fun onCaptchaCompleted(
        currentState: PhoneInputState,
        action: PhoneInputAction.OnCaptchaCompleted
    ): ReducerResult<PhoneInputState> {
        return ReducerResult(
            currentState.copy(showCaptchaInputBottomSheet = false, captchaModel = action.model)
        )
    }

    private fun submitPhoneAndPassword(
        currentState: PhoneInputState
    ): ReducerResult<PhoneInputState> {
        return if (currentState.captchaModel == null) {
            return ReducerResult(
                currentState.copy(
                    showCaptchaInputBottomSheet = false
                ),
                oneShotEvents = arrayOf(SnackbarOptions("Не удалось загрузить каптчу. Попробуйте позже").toOneShotEvent())
            )
        } else {
            ReducerResult(
                currentState.copy(
                    showCaptchaInputBottomSheet = true
                )
            )
        }
    }

    private fun onUserWantChangedCountry(
        currentState: PhoneInputState
    ): ReducerResult<PhoneInputState> {
        return ReducerResult(currentState.copy(showCountrySelectDialog = true))
    }

    private fun onSignInFailed(
        currentState: PhoneInputState,
        action: PhoneInputAction.SignInFailed
    ): ReducerResult<PhoneInputState> {
        val message = action.throwable.message ?: "Произошла ошибка"
        return ReducerResult(
            currentState.copy(
                isLoading = false,
                exception = action.throwable
            ),
            oneShotEvents = arrayOf(SnackbarOptions(message).toOneShotEvent())
        )
    }

    private fun onSignInConfirmed(
        currentState: PhoneInputState,
        action: PhoneInputAction.SignInConfirmed
    ): ReducerResult<PhoneInputState> {
        val message = "User ${action.profileModel.firstName} signed in"

        return ReducerResult(
            currentState.copy(
                isLoading = false,
                profileModel = action.profileModel
            ),
            oneShotEvents = arrayOf(
                SnackbarOptions(message).toOneShotEvent(),
                ToastEventData(message.toSOR()).toOneShotEvent()
            )
        )
    }

    private fun onCountrySelected(
        action: PhoneInputAction.SelectedCountry,
        currentState: PhoneInputState
    ): ReducerResult<PhoneInputState> {
        return ReducerResult(
            currentState.copy(
                country = action.phoneNumberMasks,
                showCountrySelectDialog = false
            )
        )
    }

    private fun onPhoneNumberChanged(
        action: PhoneInputAction.PhoneNumberInserted,
        currentState: PhoneInputState
    ): ReducerResult<PhoneInputState> {
        return ReducerResult(
            currentState.copy(
                phoneNumber = action.actualValue
            )
        )
    }

    private fun onPasswordChanged(
        action: PhoneInputAction.PasswordInserted,
        currentState: PhoneInputState
    ): ReducerResult<PhoneInputState> {
        return ReducerResult(
            currentState.copy(
                password = action.actualValue
            )
        )
    }
}