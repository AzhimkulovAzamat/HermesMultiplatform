package stores

import helper.TestLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.breez.hermes.android.middleware.RegistrationCommand
import net.breez.hermes.android.middleware.RegistrationMiddleWare
import net.breez.hermes.android.model.PhoneNumberMasks
import net.breez.hermes.android.model.SnackbarOptions
import net.breez.hermes.android.model.toSOR
import net.breez.hermes.android.mvi.redux.OneShotEvent
import net.breez.hermes.android.mvi.redux.ToastEventData
import net.breez.hermes.android.ui.screen.signin.PhoneInputAction
import net.breez.hermes.android.ui.screen.signin.PhoneInputBootstrapper
import net.breez.hermes.android.ui.screen.signin.PhoneInputPostProcessor
import net.breez.hermes.android.ui.screen.signin.PhoneInputReducer
import net.breez.hermes.android.ui.screen.signin.SignInViewModel
import net.breez.hermes.domain.model.CaptchaModel
import net.breez.hermes.domain.model.TestModel
import net.breez.hermes.domain.model.base.FlowResult
import net.breez.hermes.domain.repositories.TestRepository
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class MainDispatcherRule(val dispatcher: TestDispatcher = TestCoroutineDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) = Dispatchers.setMain(dispatcher)

    override fun finished(description: Description?) = Dispatchers.resetMain()

}

@OptIn(ExperimentalCoroutinesApi::class)
class TestSignIn {
    private val phoneInputPostProcessor = mock<PhoneInputPostProcessor> {}
    private val testRepository = mock<TestRepository> {}
    private val registrationMiddleWare = mock<RegistrationMiddleWare>()

    private val phoneInputBootstrapper = PhoneInputBootstrapper(testRepository)
    private val reducer = PhoneInputReducer()
    private val logger = TestLogger()

    private val viewModel = SignInViewModel(
        registrationMiddleWare,
        phoneInputPostProcessor,
        phoneInputBootstrapper,
        reducer,
        logger
    )

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `correct input phone number`() = runTest {
        viewModel.sendAction(PhoneInputAction.PhoneNumberInserted("705999888"))
        assertEquals("705999888", viewModel.state.value.current.phoneNumber)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `correct input password`() = runTest {
        viewModel.sendAction(PhoneInputAction.PasswordInserted("<PASSWORD>"))
        assertEquals("<PASSWORD>", viewModel.state.value.current.password)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `open captcha bottom sheet failed because of captcha is null`() =
        runTest(UnconfinedTestDispatcher()) {
            val deferred = async {
                viewModel.viewEffect.first()
            }
            viewModel.sendAction(PhoneInputAction.SubmitPhoneAndPassword)
            assertFalse(viewModel.state.value.current.showCaptchaInputBottomSheet)
            val effect = deferred.await()
            assertTrue { effect is OneShotEvent.ShowSnackBar }
            assertEquals(
                (effect as? OneShotEvent.ShowSnackBar)?.snackbarOptions?.toString(),
                SnackbarOptions("Не удалось загрузить каптчу. Попробуйте позже").toString()
            )
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `success open captcha bottom sheet`() = runTest {
        val captcha = CaptchaModel("asdfasdfasd", "sdfasdfsd")
        doReturn(FlowResult.Success(captcha)).`when`(testRepository).getCaptcha()
        viewModel.onCreate()
        viewModel.sendAction(PhoneInputAction.SubmitPhoneAndPassword)
        assertTrue(viewModel.state.value.current.showCaptchaInputBottomSheet)
        assertEquals(captcha, viewModel.state.value.current.captchaModel)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `success open country select bottom sheet`() = runTest {
        viewModel.sendAction(PhoneInputAction.ShowCountryOptions)
        assertTrue(viewModel.state.value.current.showCountrySelectDialog)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `correct select country`() = runTest {
        viewModel.sendAction(PhoneInputAction.SelectedCountry(PhoneNumberMasks.KG))
        assertEquals(PhoneNumberMasks.KG, viewModel.state.value.current.country)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `successful signing`() = runTest(UnconfinedTestDispatcher()) {
        val testModel = TestModel("Azamat")
        val message = "User ${testModel.firstName} signed in"
        val emissions = mutableListOf<OneShotEvent?>()
        val deferred = launch {
            viewModel.viewEffect.toList(emissions)
        }
        viewModel.sendAction(PhoneInputAction.SignInConfirmed(testModel))
        deferred.cancel()
        assertEquals(testModel, viewModel.state.value.current.profileModel)
        assertFalse(viewModel.state.value.current.isLoading)
        assertEquals(2, emissions.size)
        assertTrue { emissions.first() is OneShotEvent.ShowSnackBar }
        assertTrue { emissions.last() is OneShotEvent.ShowToast }
        assertEquals(SnackbarOptions(message).toString(), (emissions.first() as OneShotEvent.ShowSnackBar).snackbarOptions.toString())
        assertEquals(ToastEventData(message.toSOR()).toString(), (emissions.last() as OneShotEvent.ShowToast).toast.toString())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `signing failed`() = runTest(UnconfinedTestDispatcher()) {
        val message = "Test error"
        val throwable = Throwable(message)
        val deferred = async {
            viewModel.viewEffect.first()
        }
        viewModel.sendAction(PhoneInputAction.SignInFailed(throwable))
        assertEquals(throwable, viewModel.state.value.current.exception)
        assertNull(viewModel.state.value.current.profileModel)
        assertEquals(SnackbarOptions(message).toString(), (deferred.await() as? OneShotEvent.ShowSnackBar)?.snackbarOptions.toString())
    }
}