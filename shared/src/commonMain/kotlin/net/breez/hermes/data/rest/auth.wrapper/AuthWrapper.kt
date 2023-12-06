package net.breez.hermes.data.rest.auth.wrapper

import io.ktor.utils.io.core.toByteArray
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

interface AuthWrapper {
    fun <T> wrap(onToken: (String) -> T): T
    suspend fun <T> suspendWrap(onToken: suspend (String) -> T): T
}

@OptIn(ExperimentalEncodingApi::class)
class AuthWrapperImpl : AuthWrapper {
    companion object {
        const val TEST_TOKEN = "505facac-53e9-4bfb-9044-4d629fa89570:"
    }

    override fun <T> wrap(onToken: (String) -> T): T {
        val token = TEST_TOKEN
        val base64 = Base64.encode(token.toByteArray(), 0).replace("\n", "")
        val readyToken = "Basic $base64"

        return onToken(readyToken)
    }

    override suspend fun <T> suspendWrap(onToken: suspend (String) -> T): T {
        val token = TEST_TOKEN
        val base64 = Base64.encode(token.toByteArray(), 0).replace("\n", "")
        val readyToken = "Basic $base64"

        return onToken(readyToken)
    }
}