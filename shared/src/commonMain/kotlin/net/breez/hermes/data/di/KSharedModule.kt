package net.breez.hermes.data.di

import net.breez.hermes.data.repositories.TestDataRepository
import net.breez.hermes.data.rest.KtorRestClient
import net.breez.hermes.data.rest.RestClient
import net.breez.hermes.domain.repositories.TestRepository
import org.koin.dsl.module

val sharedModule = module {
    single<RestClient> { KtorRestClient() }
    single<TestRepository> { TestDataRepository(get()) }
}