package net.breez.hermes.data.rest

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import net.breez.hermes.data.entity.CaptchaEntity
import net.breez.hermes.data.entity.TestEntity
import net.breez.hermes.data.entity.response.BaseResponse
import net.breez.hermes.data.rest.api.TestApi
import net.breez.hermes.data.rest.exception.UnknownHttpException
import net.breez.hermes.domain.model.base.FlowResult

class KtorRestClient : RestClient {

    private val BASE_URL = "https://api-devkdk.kulikov.com/"

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    override fun getTestApi(): TestApi {

        return object : TestApi {
            override suspend fun getKdkProfileModel(token: String): FlowResult<BaseResponse<TestEntity>> {
                val response = httpClient.get(BASE_URL + "v2/client/profile") {
                    header("Authorization", token)
                }
                return response.toFlowResult()
            }

            override suspend fun getCaptcha(): FlowResult<BaseResponse<CaptchaEntity>> {
                val response = httpClient.get(BASE_URL + "v2/client/get-captcha")
                return response.toFlowResult()
            }
        }
    }
}

suspend inline fun <reified T> HttpResponse.toFlowResult(): FlowResult<T> {
    return if (this.status.value == 200) {
        FlowResult.Success(this.body())
    } else {
        FlowResult.Exception(UnknownHttpException(this.status.value))
    }
}