package net.breez.hermes.domain.model.base

sealed class FlowResult<out T> {

    class Success<T>(val element: T) : FlowResult<T>() {
        override fun <E> map(transform: (T) -> E): FlowResult<E> {
            return Success(transform(element))
        }
    }

    class Exception(val throwable: Throwable) : FlowResult<Nothing>() {
        override fun <E> map(transform: (Nothing) -> E): FlowResult<E> {
            return this
        }
    }

    abstract fun <E> map(transform: (T) -> E): FlowResult<E>
}
