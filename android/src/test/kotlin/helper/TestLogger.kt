package helper

import android.util.Log
import net.breez.hermes.android.mvi.redux.Command
import net.breez.hermes.android.mvi.redux.EmptyEvent
import net.breez.hermes.android.mvi.redux.Event
import net.breez.hermes.android.mvi.redux.Logger
import net.breez.hermes.android.mvi.redux.LoggerImpl
import net.breez.hermes.android.mvi.redux.OneShotEvent
import net.breez.hermes.android.mvi.redux.State
import net.breez.hermes.android.mvi.redux.StateAction
import net.breez.hermes.android.mvi.redux.UndefinedCommand

class TestLogger: Logger {

    override fun log(message: String) {

    }

    override fun log(state: State) {

    }

    override fun log(oneShotEvent: OneShotEvent) {

    }

    override fun log(command: Command) {

    }

    override fun log(action: StateAction) {

    }

    override fun log(event: Event) {

    }
}