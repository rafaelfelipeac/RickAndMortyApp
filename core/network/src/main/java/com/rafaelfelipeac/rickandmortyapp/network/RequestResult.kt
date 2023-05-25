package com.rafaelfelipeac.rickandmortyapp.network

sealed interface RequestResult<T : Any>

class RequestSuccess<T : Any>(val data: T) : RequestResult<T>
class RequestError<T : Any>(val code: Int, val message: String?) : RequestResult<T>
class RequestException<T : Any>(val e: Throwable) : RequestResult<T>
