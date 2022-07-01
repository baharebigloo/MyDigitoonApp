package com.example.mydigitoonapp.util

class Resource<out T>(val status: Status, val data: T?, val throwable: Throwable?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = Status.SUCCESS, data = data, throwable = null)

        fun <T> error(data: T?, throwable: Throwable): Resource<T> = Resource(status = Status.ERROR, data = data, throwable = throwable)

        fun <T> loading(data: T?): Resource<T> = Resource(status = Status.LOADING, data = data, throwable = null)
    }
}