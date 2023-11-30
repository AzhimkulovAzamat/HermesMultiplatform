package net.breez.hermes.android.ui.screen.signin.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.breez.hermes.android.R
import net.breez.hermes.android.model.toSOR
import net.breez.hermes.android.ui.custom.BaseButton
import net.breez.hermes.android.ui.custom.ImageView
import net.breez.hermes.android.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CaptchaInputBottomSheet(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onDismissRequest: () -> Unit = { }
) {

    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    val title = context.getString(R.string.title_captchaBottomSheet)

    ModalBottomSheet(
        modifier = modifier.fillMaxHeight(),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        windowInsets = WindowInsets.ime
    ) {
        Column(Modifier.background(Color.White)) {
            Text(
                text = title,
                style = Typography.mainTitle,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            Divider(color = CardStroke, modifier = Modifier.padding(16.dp))

            ImageView(
                resourceId = R.drawable.test_captcha,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

            BasicTextField(
                value = value,
                onValueChange,
                textStyle = Typography.mediumTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .background(SoftPink, RoundedCornerShape(8.dp))
                    .border(1.dp, LightPink, RoundedCornerShape(8.dp))
                    .padding(vertical = 14.dp, horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                decorationBox = { innerTextField ->
                    Row {
                        if (value.isEmpty())
                            Text(
                                text = "Введите код",
                                color = HintColor,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = fonts
                            )
                        innerTextField.invoke()
                    }
                }
            )

            Row(
                Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ImageView(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    resourceId = R.drawable.arrow_circle_light_purple
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = "Другой код",
                    fontFamily = fonts,
                    color = LightPurple,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500
                )
            }

            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp, bottom = 36.dp)
                    .padding(horizontal = 16.dp),
                title = R.string.baseButtonTitle_captchaBottomSheet.toSOR()
            )
        }
    }
}
