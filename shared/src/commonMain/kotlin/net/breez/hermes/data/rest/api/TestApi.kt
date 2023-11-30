package net.breez.hermes.data.rest.api

import net.breez.hermes.data.entity.TestEntity
import net.breez.hermes.data.entity.response.BaseResponse
import net.breez.hermes.domain.model.base.FlowResult

interface TestApi {
    suspend fun getKdkProfileModel(token:String): FlowResult<BaseResponse<TestEntity>>
}