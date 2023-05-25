package com.rafaelfelipeac.rickandmortyapp.core.network

import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> handleRequest(
    execute: suspend () -> Response<T>
): RequestResult<T> {
    return try {
        val response = execute()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            RequestSuccess(body)
        } else {
            RequestError(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        RequestError(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        RequestException(e)
    }
}
