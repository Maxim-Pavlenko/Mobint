package com.example.mobint.util

sealed class State {
    object Error: State()
    object Loading: State()
    object Success: State()

    object Empty: State()
}