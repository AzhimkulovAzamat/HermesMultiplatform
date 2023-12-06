package net.breez.hermes.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val success: Boolean,
    val status: Int,
    val data: T
)