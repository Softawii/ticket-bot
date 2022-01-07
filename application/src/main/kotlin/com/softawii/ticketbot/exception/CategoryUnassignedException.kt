package com.softawii.ticketbot.exception

class CategoryUnassignedException : RuntimeException {

    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
}