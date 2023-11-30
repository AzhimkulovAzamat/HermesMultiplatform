package net.breez.hermes.android.model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import net.breez.hermes.android.mvi.redux.OneShotEvent

class SnackbarOptions(
    override val message: String,
    val icon: Int = androidx.core.R.drawable.ic_call_answer,
    private val actionIcon: Int? = androidx.core.R.drawable.ic_call_answer_low,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = true
) : SnackbarVisuals {
    override val actionLabel: String?
        get() = actionIcon?.toString()

    fun toOneShotEvent(): OneShotEvent.ShowSnackBar {
        return OneShotEvent.ShowSnackBar(this)
    }
}