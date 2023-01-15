package com.mperezc.domain.usecases

sealed class Result<out R> {

    class Success<out T>(val data: T) : Result<T>()
    class Error(val exception: Int) : Result<Nothing>()

}

val Result<*>.succeeded
    get() = this is Result.Success