package net.breez.hermes.data.rest

import net.breez.hermes.data.rest.api.TestApi

interface RestClient {
    fun getTestApi(): TestApi
}