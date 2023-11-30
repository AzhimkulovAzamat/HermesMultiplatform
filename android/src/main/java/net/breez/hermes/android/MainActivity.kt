package net.breez.hermes.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import net.breez.hermes.android.ui.screen.signin.SignInScreen
import net.breez.hermes.android.ui.screen.signin.SignInViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val signInViewModel:SignInViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                SignInScreen(signInViewModel)
            }
        }
    }
}
