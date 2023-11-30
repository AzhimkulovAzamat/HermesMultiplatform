package net.breez.hermes.android.mvi.redux

interface PostProcessor<E : Event, A : StateAction> {

    fun process(event: E): A
}

interface PostProcessorContainer {

    suspend fun process(event: Event): StateAction?

    fun defaultAction(): StateAction? = null
}