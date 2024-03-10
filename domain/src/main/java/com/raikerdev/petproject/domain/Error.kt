package com.raikerdev.petproject.domain

sealed interface Error {
    class Server(val code: Int) : Error
    data object Connectivity : Error
    class Unknown(val message: String) : Error
}
