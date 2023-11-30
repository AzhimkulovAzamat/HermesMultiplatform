package net.breez.hermes.android.ui.custom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.breez.hermes.android.model.StringOrResource
import net.breez.hermes.android.ui.theme.MainPurple
import net.breez.hermes.android.ui.theme.fonts

@Composable
fun ImageView(
    @DrawableRes resourceId: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    val image: Painter = painterResource(id = resourceId)
    Image(
        painter = image,
        contentDescription,
        modifier,
        alignment, contentScale, alpha, colorFilter
    )
}

@Composable
@Preview
fun BaseButton(
    modifier: Modifier = Modifier,
    title: StringOrResource = StringOrResource.empty("Войти"),
    onClick: () -> Unit = {}
) {

    val context = LocalContext.current

    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MainPurple),
        contentPadding = PaddingValues(vertical = 14.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = title.getValue(context),
            fontFamily = fonts,
            color = Color.White,
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.W500
        )
    }
}