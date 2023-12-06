package net.breez.hermes.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CaptchaEntity (
    val image_url: String,
    val captcha_id: Int
)