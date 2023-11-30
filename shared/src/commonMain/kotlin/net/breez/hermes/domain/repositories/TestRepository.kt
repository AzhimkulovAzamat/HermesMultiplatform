package net.breez.hermes.domain.repositories

import net.breez.hermes.domain.model.TestModel
import net.breez.hermes.domain.model.base.FlowResult

interface TestRepository {
    suspend fun getKdkProfileModel(): FlowResult<TestModel>
}