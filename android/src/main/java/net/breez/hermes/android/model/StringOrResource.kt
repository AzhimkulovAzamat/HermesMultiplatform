package net.breez.hermes.android.model

import android.content.Context
import androidx.annotation.StringRes

data class StringOrResource internal constructor(
    @StringRes private val resourceId: Int?,
    private val text: String?
) {

    companion object {
        fun empty(testValue:String = ""): StringOrResource = StringOrResource(testValue)
    }

    constructor(@StringRes resourceId: Int) : this(resourceId, null)
    constructor(text: String) : this(null, text)

    fun getValue(context: Context): String {
        return resourceId?.let { context.getString(it) } ?: text!!
    }
}

fun String.toSOR(): StringOrResource {
    return StringOrResource(this)
}

fun Int.toSOR(): StringOrResource {
    return StringOrResource(this)
}
