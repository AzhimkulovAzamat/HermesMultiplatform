package net.breez.hermes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform