package net.breez.hermes.android.mvi.redux

interface Middleware<out E, in C> where E: Event, C: Command {

    suspend fun process(command: C): E
}

interface MiddlewareContainer {
    suspend fun process(command: Command): Event

    fun defaultEvent(): Event = EmptyEvent()
}

interface Command: LogEvent

class UndefinedCommand: Command

interface Event: LogEvent

class EmptyEvent: Event