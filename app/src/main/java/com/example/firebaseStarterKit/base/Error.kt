package com.example.firebaseStarterKit.base

sealed class Error {

    class NetworkError() : Error()
    class EmptyBodyError(): Error()
    class RandomError(val cause: Throwable?): Error()
}
