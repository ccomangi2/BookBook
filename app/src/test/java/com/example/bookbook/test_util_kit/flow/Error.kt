package com.meuus90.booksearcher.test_util_kit.flow

internal sealed class Error {
    object Empty : Error()
    data class Wrapped(val throwable: Throwable) : Error()
}