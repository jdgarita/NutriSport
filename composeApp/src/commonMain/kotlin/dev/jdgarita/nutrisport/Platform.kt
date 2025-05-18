package dev.jdgarita.nutrisport

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform