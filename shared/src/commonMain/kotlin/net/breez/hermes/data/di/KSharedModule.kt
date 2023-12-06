package net.breez.hermes.data.di

import net.breez.hermes.data.repositories.TestDataRepository
import net.breez.hermes.data.rest.KtorRestClient
import net.breez.hermes.data.rest.RestClient
import net.breez.hermes.data.rest.auth.wrapper.AuthWrapper
import net.breez.hermes.data.rest.auth.wrapper.AuthWrapperImpl
import net.breez.hermes.domain.repositories.TestRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::KtorRestClient) bind RestClient::class
    singleOf(::TestDataRepository) bind TestRepository::class
    singleOf(::AuthWrapperImpl) bind AuthWrapper::class
}