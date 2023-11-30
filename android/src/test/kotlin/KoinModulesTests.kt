import net.breez.hermes.android.di.viewModelModules
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class KoinModulesTests: KoinTest {

    @Test
    fun checkAllModules() {
        koinApplication {
            modules(viewModelModules)
            checkModules()
        }
    }
}