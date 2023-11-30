package net.breez.hermes.data.rest.auth

interface AuthWrapper {
    fun <T> wrap(onToken: (String) -> T): T
    suspend fun <T> suspendWrap(onToken: suspend (String) -> T): T
}