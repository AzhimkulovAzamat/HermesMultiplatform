package net.breez.hermes.data.repositories

import net.breez.hermes.data.rest.RestClient
import net.breez.hermes.data.rest.auth.wrapper.AuthWrapper
import net.breez.hermes.domain.model.CaptchaModel
import net.breez.hermes.domain.model.TestModel
import net.breez.hermes.domain.model.base.FlowResult
import net.breez.hermes.domain.repositories.TestRepository

class TestDataRepository(
    private val restClient: RestClient,
    private val authWrapper: AuthWrapper
) : TestRepository {

    override suspend fun getKdkProfileModel(): FlowResult<TestModel> {
        return authWrapper.suspendWrap { token ->
            restClient.getTestApi().getKdkProfileModel(token).map {
                TestModel(it.data.first_name)
            }
        }
    }

    override suspend fun getCaptcha(): FlowResult<CaptchaModel> {
        return restClient.getTestApi().getCaptcha().map {
            CaptchaModel(it.data.image_url, it.data.captcha_id.toString())
        }
    }
}