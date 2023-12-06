package net.breez.hermes.android.ui.screen.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.launch
import net.breez.hermes.android.R
import net.breez.hermes.android.mvi.redux.OneShotEvent
import net.breez.hermes.android.ui.custom.ComposableLifecycle
import net.breez.hermes.android.ui.custom.CustomSnackbar
import net.breez.hermes.android.ui.custom.ImageView
import net.breez.hermes.android.ui.custom.PhoneInputView
import net.breez.hermes.android.ui.screen.extensions.collectAsEffect
import net.breez.hermes.android.ui.screen.signin.modal.captcha.CaptchaInputBottomSheet
import net.breez.hermes.android.ui.theme.*

@ExperimentalMaterial3Api
@Composable
@Preview
fun SignInScreen(viewModel: SignInViewModel? = null) {
    viewModel ?: return

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState()

    ComposableLifecycle(viewModel) { states ->
        val currentState = states.current

        if (currentState.showCaptchaInputBottomSheet) {
            CaptchaInputBottomSheet(
                initialCaptcha = currentState.captchaModel!!,
                onCaptchaCompleted = { viewModel.sendAction(PhoneInputAction.OnCaptchaCompleted(it)) }
            )
        }

        if (currentState.showCountrySelectDialog) {
            ModalBottomSheet(onDismissRequest = {
                viewModel.sendAction(PhoneInputAction.ShowCountryOptions)
            }, sheetState = sheetState) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        ImageView(
                            resourceId = R.drawable.kyrgyzstan_flag,
                            Modifier
                                .padding(start = 19.dp)
                                .padding(vertical = 11.dp)
                        )
                        Text(
                            text = "Кыргызстан",
                            Modifier
                                .padding(start = 19.dp)
                                .fillMaxWidth(),
                            style = Typography.smallTitle
                        )
                        Divider(color = SeparatorColor)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageView(
                            resourceId = R.drawable.kyrgyzstan_flag,
                            Modifier
                                .padding(start = 19.dp)
                                .padding(vertical = 11.dp)
                        )
                        Text(
                            text = "Кыргызстан",
                            Modifier
                                .padding(start = 19.dp)
                                .fillMaxWidth(),
                            style = Typography.smallTitle
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageView(
                            resourceId = R.drawable.kyrgyzstan_flag,
                            Modifier
                                .padding(start = 19.dp)
                                .padding(vertical = 11.dp)
                        )
                        Text(
                            text = "Кыргызстан",
                            Modifier
                                .padding(start = 19.dp)
                                .fillMaxWidth(),
                            style = Typography.smallTitle
                        )
                    }
                }
            }
        }

        Surface(
            Modifier.fillMaxSize(),
            color = Color.White
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (backgroundCircle, mascot, phoneInput, passwordField, submitButton, snackbar) = createRefs()

                ImageView(
                    resourceId = R.drawable.phone_number_input_circle,
                    modifier = Modifier.constrainAs(backgroundCircle) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                )
                ImageView(
                    resourceId = R.drawable.phone_number_input_mascot,
                    modifier = Modifier
                        .size(210.dp, 210.dp)
                        .constrainAs(mascot) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(top = 52.dp)
                )
                PhoneInputView(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .constrainAs(phoneInput) {
                            top.linkTo(mascot.bottom)
                        },
                    currentState.phoneNumber,
                    onUserWantChangeCountry = {
                        viewModel.sendAction(PhoneInputAction.ShowCountryOptions)
                    }
                ) {
                    viewModel.sendAction(PhoneInputAction.PhoneNumberInserted(it))
                }
                BasicTextField(
                    value = currentState.password,
                    onValueChange = { viewModel.sendAction(PhoneInputAction.PasswordInserted(it)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    textStyle = Typography.mediumTitle,
                    modifier = Modifier
                        .constrainAs(passwordField) {
                            top.linkTo(phoneInput.bottom)
                        }
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                        .background(SoftPink, RoundedCornerShape(8.dp))
                        .border(1.dp, LightPink, RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                        .fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            if (currentState.password.isEmpty())
                                Text(
                                    text = "Пароль",
                                    color = HintColor,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.W700,
                                    fontFamily = fonts
                                )
                            innerTextField()
                        }
                    }
                )

                Button(
                    onClick = { viewModel.sendAction(PhoneInputAction.SubmitPhoneAndPassword) },
                    enabled = !currentState.isLoading,
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    modifier = Modifier
                        .constrainAs(submitButton) {
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
                        .background(CardFill, RoundedCornerShape(8.dp))
                        .border(2.dp, MainPurple, RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Зарегаться",
                        color = SubtitleGray,
                        fontFamily = fonts,
                        fontWeight = FontWeight.W500,
                        fontSize = 17.sp
                    )
                }
                viewModel.viewEffect.collectAsEffect { event ->
                    when (event) {
                        is OneShotEvent.ShowSnackBar -> {
                            scope.launch {
                                snackbarHostState.showSnackbar(event.snackbarOptions)
                            }
                        }

                        is OneShotEvent.ShowToast -> {
                            Toast.makeText(
                                context,
                                event.toast.message.getValue(context),
                                event.toast.duration
                            )
                                .show()
                        }

                        is OneShotEvent.Navigation -> {}
                        else -> {}
                    }
                }

                SnackbarHost(
                    snackbarHostState,
                    modifier = Modifier.constrainAs(snackbar) {
                        top.linkTo(parent.top)
                    }
                ) {
                    CustomSnackbar(snackbarData = it)
                }
            }

            if (currentState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}