import net.breez.hermes.android.di.viewModelModules
import net.breez.hermes.android.ui.screen.signin.modal.captcha.CaptchaInputViewModel
import net.breez.hermes.domain.model.CaptchaModel
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.CheckParameters
import org.koin.test.check.checkModules

class KoinModulesTests : KoinTest {

    @Test
    fun checkAllModules() {
        koinApplication {
            modules(viewModelModules)
            checkModules {
                withParameter<CaptchaInputViewModel> { parametersOf(CaptchaModel("", "")) }
            }
        }
    }
}