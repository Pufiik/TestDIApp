package ru.pugovishnikova.example.testdiapp.utils

sealed class State<out T> {
    class Idle : State<Nothing>()
    class Loading : State<Nothing>()
    class Success<out T>(val data:T) : State<T>()
    class Fail(val exception: Throwable) : State<Nothing>()
}
