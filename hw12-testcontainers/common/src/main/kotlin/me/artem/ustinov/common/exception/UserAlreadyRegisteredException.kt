package me.artem.ustinov.common.exception

class UserAlreadyRegisteredException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}