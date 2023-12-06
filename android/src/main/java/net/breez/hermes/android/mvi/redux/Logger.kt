package net.breez.hermes.android.mvi.redux

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder

interface Logger {

    fun log(message: String)
    fun log(state: State)
    fun log(oneShotEvent: OneShotEvent)
    fun log(command: Command)
    fun log(action: StateAction)
    fun log(event: Event)
}

class LoggerImpl : Logger {

    companion object {
        const val LOGGER_TAG = "LOGGER_MIDDLEWARE"
    }

    override fun log(message: String) {
        Log.d(LOGGER_TAG, message)
    }

    override fun log(state: State) {
        Log.d(LOGGER_TAG, "<-- ${state::class.java.simpleName} updated to ${state.toJson()}")
    }

    override fun log(oneShotEvent: OneShotEvent) {
        Log.d(LOGGER_TAG, "<-- Published ${oneShotEvent::class.java.simpleName} view effect with ${oneShotEvent.toJson()}")
    }

    override fun log(command: Command) {
        if (command is UndefinedCommand) {
            return
        }

        Log.d(LOGGER_TAG, "--> Performed ${command::class.java.simpleName} command with ${command.toJson()}")
    }

    override fun log(action: StateAction) {
        Log.d(LOGGER_TAG, "--> Performed ${action::class.java.simpleName} action with ${action.toJson()}")
    }

    override fun log(event: Event) {
        if (event is EmptyEvent) {
            return
        }

        Log.d(LOGGER_TAG, "<-- Published ${event::class.java.simpleName} event with ${event.toJson()}")
    }
}

interface LogEvent {
    fun toJson(): String = GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(this)
}