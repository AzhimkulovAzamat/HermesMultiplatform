package net.breez.hermes.android.model

import androidx.annotation.DrawableRes
import net.breez.hermes.android.R

enum class PhoneNumberMasks(
    @DrawableRes val flagId:Int,
    val countryCode:String,
    val countryName: String,
    val mask:String,
    val id:Int
) {
    KG(R.drawable.kyrgyzstan_flag, "+996", "Кыргызстан","000 00 00 00", 99),
    KZ(R.drawable.kyrgyzstan_flag, "+7", "Казахстан","000 000 00 00", 81),
    UZ(R.drawable.kyrgyzstan_flag, "+998", "Узбекистан","00 000 00 00", 191),
    RU(R.drawable.kyrgyzstan_flag, "+7", "Россия","000 000 00 00", 0);

    companion object {
        fun fromId(id:Int): PhoneNumberMasks {
            for (item in values()) {
                if (item.id == id) {
                    return item
                }
            }

            return KG
        }
    }
}