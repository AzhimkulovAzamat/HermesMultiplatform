package net.breez.hermes.android.mvi.redux

interface Bootstrapper<A: StateAction> {
    suspend fun viewDidLoad(newAction: suspend (A) -> Unit) {}

    suspend fun viewReload(newAction: suspend (A) -> Unit) {}
}