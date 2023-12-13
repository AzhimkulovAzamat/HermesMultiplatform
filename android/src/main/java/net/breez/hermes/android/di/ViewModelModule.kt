package net.breez.hermes.android.di

import net.breez.hermes.android.middleware.RegistrationMiddleWare
import net.breez.hermes.android.mvi.redux.Logger
import net.breez.hermes.android.mvi.redux.LoggerImpl
import net.breez.hermes.android.ui.screen.signin.PhoneInputBootstrapper
import net.breez.hermes.android.ui.screen.signin.PhoneInputPostProcessor
import net.breez.hermes.android.ui.screen.signin.PhoneInputReducer
import net.breez.hermes.android.ui.screen.signin.SignInViewModel
import net.breez.hermes.android.ui.screen.signin.modal.captcha.CaptchaInputPostProcessor
import net.breez.hermes.android.ui.screen.signin.modal.captcha.CaptchaInputReducer
import net.breez.hermes.android.ui.screen.signin.modal.captcha.CaptchaInputViewModel
import net.breez.hermes.data.di.sharedModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val viewModelModules = module {
    includes(sharedModule)
    single<Logger> { LoggerImpl() }
    singleOf(::PhoneInputReducer)
    singleOf(::PhoneInputBootstrapper)
    singleOf(::PhoneInputPostProcessor)
    singleOf(::RegistrationMiddleWare)
    singleOf(::CaptchaInputReducer)
    singleOf(::CaptchaInputPostProcessor)

    viewModelOf(::SignInViewModel)
    viewModel { CaptchaInputViewModel(it.get(), get(), get(), get(), get()) }
}