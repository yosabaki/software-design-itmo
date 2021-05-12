package me.artem.ustinov.common.exception

class NotEnoughMoneyException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}