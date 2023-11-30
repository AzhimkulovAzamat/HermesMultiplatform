package net.breez.hermes.android.middleware

import net.breez.hermes.android.mvi.redux.Command
import net.breez.hermes.android.mvi.redux.Event
import net.breez.hermes.android.mvi.redux.Middleware
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
        }
    }
}

sealed class RegistrationEvent : Event {
    class SuccessSignIn(val profileModel: TestModel) : RegistrationEvent()
    class FailedSignIn(val throwable: Throwable) : RegistrationEvent()
}

sealed class RegistrationCommand : Command {
    class SignIn(val phoneNumber: String, val password: String) : RegistrationCommand()
}