package com.softawii.ticketbot.internal

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.ChannelType.*
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import java.lang.reflect.Method
import java.util.stream.Collectors

class CommandHandler(
    private val permissions: List<Permission>,
    private val method: Method,
    private val environment: Environment
) {

    companion object {
        val ALLOWED_CHANNELS = listOf(TEXT, GUILD_PUBLIC_THREAD, GUILD_PRIVATE_THREAD)
    }

    fun execute(event: SlashCommandEvent): String {
        if (event.channelType == PRIVATE) {
            if (!isPrivateEnvironment()) {
                return "Esse comando não pode ser executado em um canal privado"
            }
            return execute(event, event.user)
        } else if (ALLOWED_CHANNELS.contains(event.channelType)) {
            if (!isServerEnvironment()) {
                return "Esse comando não pode ser executado em um canal de servidor"
            }
            return execute(event, event.member!!)
        }
        return "Canal não permitido"
    }

    private fun isPrivateEnvironment(): Boolean {
        return environment == Environment.PRIVATE || environment == Environment.BOTH
    }

    private fun isServerEnvironment(): Boolean {
        return environment == Environment.SERVER || environment == Environment.BOTH
    }

    private fun execute(event: SlashCommandEvent, user: User): String {
        return method.invoke(null, event) as String
    }

    private fun execute(event: SlashCommandEvent, member: Member): String {
        if (member.hasPermission(permissions)) {
            return method.invoke(null, event) as String
        } else {
            return "Você não tem permissões (${requiredPermissionsToString()}) suficientes para executar esse comando"
        }
    }

    private fun requiredPermissionsToString(): String {
        return permissions.stream().map(Permission::getName).collect(Collectors.joining(", "))
    }

    fun getMethodPath(): String {
        return "${method.declaringClass.name}.${method.name}()"
    }
}