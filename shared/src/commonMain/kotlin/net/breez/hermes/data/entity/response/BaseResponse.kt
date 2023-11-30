package net.breez.hermes.data.entity.response

data class BaseResponse<T>(
    val success: Boolean,
    val status: Int,
    val data: T
)