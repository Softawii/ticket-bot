package com.softawii.ticketbot.annotation

@Target(AnnotationTarget.FUNCTION)
annotation class Command(
    val name: String = ""
)