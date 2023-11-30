package net.breez.hermes.data.rest

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import net.breez.hermes.data.entity.TestEntity
import net.breez.hermes.data.entity.response.BaseResponse
import net.breez.hermes.data.rest.api.TestApi
import net.breez.hermes.domain.model.base.FlowResult

class KtorRestClient : RestClient {

    private val BASE_URL = "https://api-devkdk.kulikov.com/"

    private val httpClient = HttpClient()

    override fun getTestApi(): TestApi {

        return object : TestApi {
            override suspend fun getKdkProfileModel(token: String): FlowResult<BaseResponse<TestEntity>> {
                return httpClient.get(BASE_URL + "v2/client/profile") {
                    header("Authorization", token)
                }.body()
            }
        }
    }
}