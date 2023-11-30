package net.breez.hermes.android.mvi.redux

import android.widget.Toast
import androidx.annotation.IntRange
import net.breez.hermes.android.model.SnackbarOptions
import net.breez.hermes.android.model.StringOrResource

sealed class OneShotEvent: LogEvent {
    class ShowSnackBar(val snackbarOptions: SnackbarOptions) : OneShotEvent()
    class ShowToast(val toast: ToastEventData) : OneShotEvent()
    data object Navigation : OneShotEvent()
}

class ToastEventData(
    val message: StringOrResource,
    @IntRange(
        Toast.LENGTH_SHORT.toLong(),
        Toast.LENGTH_LONG.toLong()
    ) val duration: Int = Toast.LENGTH_SHORT
) {

    fun toOneShotEvent(): OneShotEvent.ShowToast {
        return OneShotEvent.ShowToast(this)
    }
}