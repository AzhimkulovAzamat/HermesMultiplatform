package net.breez.hermes.android.mvi.redux

interface Reducer<S: State, A: StateAction> {
    fun reduce(currentState: S, action: A): ReducerResult<S>
}