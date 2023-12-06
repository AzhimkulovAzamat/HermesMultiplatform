package net.breez.hermes.domain.model

data class CaptchaModel(
    val image: String,
    val captchaId: String
) {
    companion object {
        fun empty(): CaptchaModel = CaptchaModel("", "")
    }
}