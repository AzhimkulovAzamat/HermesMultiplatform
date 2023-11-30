package net.breez.hermes.android.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.breez.hermes.android.R
import net.breez.hermes.android.ui.theme.CardStroke
import net.breez.hermes.android.ui.theme.HintColor
import net.breez.hermes.android.ui.theme.LightPink
import net.breez.hermes.android.ui.theme.SoftPink
import net.breez.hermes.android.ui.theme.Typography
import net.breez.hermes.android.ui.theme.fonts
import net.breez.hermes.android.ui.theme.mediumTitle

@Preview
@Composable
fun PhoneInputView(
    modifier: Modifier = Modifier,
    value: String = "",
    onUserWantChangeCountry: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SoftPink, RoundedCornerShape(8.dp))
            .border(1.dp, LightPink, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .clickable { onUserWantChangeCountry() }
        ) {
            ImageView(
                resourceId = R.drawable.kyrgyzstan_flag,
                modifier = Modifier.padding(start = 16.dp, top = 13.dp, bottom = 13.dp)
            )
            ImageView(
                resourceId = R.drawable.chevron_down,
                modifier = Modifier.padding(start = 8.dp, end = 12.dp)
            )
        }
        Divider(
            color = CardStroke,
            modifier = Modifier
                .height(24.dp)
                .width(1.dp)
        )
        Text(
            text = "+996",
            style = Typography.mediumTitle,
            modifier = Modifier.padding(start = 12.dp)
        )
        BasicTextField(
            value = value,
            onValueChange,
            textStyle = Typography.mediumTitle,
            modifier = Modifier.padding(start = 12.dp),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                Row {
                    if (value.isEmpty())
                        Text(
                            text = "000 00 00 00",
                            color = HintColor,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = fonts
                        )
                    innerTextField.invoke()
                }
            }
        )
    }
}