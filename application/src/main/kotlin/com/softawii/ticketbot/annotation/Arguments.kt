package com.softawii.ticketbot.annotation

@Target(AnnotationTarget.FUNCTION)
annotation class Arguments(vararg val value: Argument)
