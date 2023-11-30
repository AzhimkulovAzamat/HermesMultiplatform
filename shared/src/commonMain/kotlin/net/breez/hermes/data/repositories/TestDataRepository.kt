package net.breez.hermes.data.repositories

import net.breez.hermes.data.rest.RestClient
import net.breez.hermes.domain.repositories.TestRepository
import net.breez.hermes.domain.model.TestModel
import net.breez.hermes.domain.model.base.FlowResult

class TestDataRepository(
    private val restClient: RestClient
) : TestRepository {

    override suspend fun getKdkProfileModel(): FlowResult<TestModel> {
        return restClient.getTestApi().getKdkProfileModel("").map {
            TestModel(it.data.first_name)
        }
    }
}