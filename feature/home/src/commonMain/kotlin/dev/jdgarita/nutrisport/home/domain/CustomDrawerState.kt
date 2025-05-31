package dev.jdgarita.nutrisport.home.domain

enum class CustomDrawerState {
    Opened,
    Closed,
}

fun CustomDrawerState.isOpened() = this == CustomDrawerState.Opened

fun CustomDrawerState.opposite() = when {
    this.isOpened() -> CustomDrawerState.Closed
    else -> CustomDrawerState.Opened
}