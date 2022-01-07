package com.softawii.ticketbot.exception

class CategoryAlreadyAssignedException: RuntimeException {

    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
}