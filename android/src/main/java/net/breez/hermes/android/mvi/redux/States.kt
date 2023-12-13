package net.breez.hermes.android.mvi.redux

data class States<S: State>(
    val previous: S?,
    val current: S
) {
    fun updateState(state: S): States<S> {
        return States(previous = current, current = state)
    }
}