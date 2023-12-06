package net.breez.hermes.android.middleware

import net.breez.hermes.android.mvi.redux.Command
import net.breez.hermes.android.mvi.redux.Event
import net.breez.hermes.android.mvi.redux.Middleware
import net.breez.hermes.domain.model.CaptchaModel
import net.breez.hermes.domain.model.TestModel
import net.breez.hermes.domain.model.base.FlowResult
import net.breez.hermes.domain.repositories.TestRepository

class RegistrationMiddleWare constructor(
    private val testRepository: TestRepository
) : Middleware<RegistrationEvent, RegistrationCommand> {

    override suspend fun process(command: RegistrationCommand): RegistrationEvent {
        return when (command) {
            is RegistrationCommand.SignIn -> {
                when (val result = testRepository.getKdkProfileModel()) {
                    is FlowResult.Success -> RegistrationEvent.SuccessSignIn(result.element)
                    is FlowResult.Exception -> RegistrationEvent.FailedSignIn(result.throwable)
                }
            }

            RegistrationCommand.LoadNewCaptcha -> {
                when (val result = testRepository.getCaptcha()) {
                    is FlowResult.Success -> RegistrationEvent.LoadCaptchaCompleted(result.element)
                    is FlowResult.Exception -> RegistrationEvent.FailedLoadCaptcha(result.throwable)
                }
            }
        }
    }
}

sealed class RegistrationEvent : Event {
    class SuccessSignIn(val profileModel: TestModel) : RegistrationEvent()
    class FailedSignIn(val throwable: Throwable) : RegistrationEvent()

    class LoadCaptchaCompleted(val element: CaptchaModel) : RegistrationEvent()
    class FailedLoadCaptcha(val throwable: Throwable) : RegistrationEvent()
}

sealed class RegistrationCommand : Command {
    class SignIn(val phoneNumber: String, val password: String) : RegistrationCommand()
    data object LoadNewCaptcha : RegistrationCommand()
}