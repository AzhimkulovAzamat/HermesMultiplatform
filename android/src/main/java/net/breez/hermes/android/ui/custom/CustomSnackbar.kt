package net.breez.hermes.android.ui.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.breez.hermes.android.R
import net.breez.hermes.android.model.SnackbarOptions
import net.breez.hermes.android.ui.theme.MainPurple
import net.breez.hermes.android.ui.theme.Typography
import net.breez.hermes.android.ui.theme.mediumTitle

@Preview
@Composable
fun CustomSnackbar(
    modifier: Modifier = Modifier, snackbarData: SnackbarData = getSnackbarData()
) {
    Surface(
        shape = RoundedCornerShape(13.dp),
        color = MainPurple,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 11.dp + 35.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            val icon =
                (snackbarData.visuals as? SnackbarOptions)?.icon ?: R.drawable.ic_snackbar_icon
            ImageView(resourceId = icon)
            Text(
                text = snackbarData.visuals.message,
                color = Color.White,
                modifier = Modifier.padding(start = 25.dp),
                style = Typography.mediumTitle
            )

            Spacer(Modifier.weight(1f))

            snackbarData.visuals.actionLabel?.let { actionLabel ->
                ImageView(
                    resourceId = actionLabel.toInt(),
                    modifier = Modifier
                        .padding(end = 14.dp)
                        .clickable { snackbarData.performAction() },
                )
            }
        }
    }
}

private fun getSnackbarData(): SnackbarData {
    return object : SnackbarData {
        override val visuals: SnackbarOptions = SnackbarOptions(
            "Доступно к списанию 120 баллов",
            R.drawable.ic_snackbar_icon,
            R.drawable.close_button,
            SnackbarDuration.Short,
            true
        )

        override fun dismiss() {}

        override fun performAction() {}
    }
}