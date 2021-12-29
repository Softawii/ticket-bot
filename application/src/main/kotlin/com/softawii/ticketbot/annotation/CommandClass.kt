package com.softawii.ticketbot.annotation

@Target(AnnotationTarget.CLASS)
annotation class CommandClass(
    val name: String = ""
)
