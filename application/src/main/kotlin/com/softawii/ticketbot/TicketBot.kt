package com.softawii.ticketbot

import com.softawii.ticketbot.annotation.Argument
import com.softawii.ticketbot.annotation.Command
import com.softawii.ticketbot.listener.MessageListener
import com.softawii.ticketbot.listener.ReadyListener
import com.softawii.ticketbot.util.AnnotationUtil
import com.softawii.ticketbot.util.PropertiesUtil
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.apache.logging.log4j.LogManager
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import java.lang.reflect.Method
import kotlin.system.exitProcess

class TicketBot {
    companion object {
        val LOGGER = LogManager.getLogger(Companion::class.java)

        private fun initAvailableCommands(pkgName: String, jda: JDA) {
            val reflections = Reflections(
                ConfigurationBuilder()
                    .filterInputsBy(FilterBuilder().includePackage(pkgName))
                    .setUrls(ClasspathHelper.forPackage(pkgName))
                    .setScanners(Scanners.SubTypes.filterResultsBy { _ -> true })
            )

            val commands = ArrayList<CommandData>()
            reflections.getSubTypesOf(Object::class.java).forEach {
                LOGGER.debug("Found class: ${it.name}")
                for (method in it.methods) {
                    if (method.isAnnotationPresent(Command::class.java)) {
                        LOGGER.debug("Adding command list: ${method.name}")
                        val commandAnnotation =
                            method.declaredAnnotations.firstOrNull { annotation -> annotation is Command } as Command
                        val commandArguments = method.declaredAnnotations.filterIsInstance<Argument>()
                        val (commandName: String, commandData) = processCommandAnnotations(
                            commandArguments,
                            commandAnnotation,
                            method
                        )

                        if (MessageListener.COMMANDS.containsKey(commandName)) {
                            LOGGER.fatal("Command $commandName is duplicated." +
                                    "Found in classes: ${MessageListener.COMMANDS[commandName]!!.declaringClass} and ${method.declaringClass}")
                            exitProcess(-1)
                        }
                        commands.add(commandData)


                        MessageListener.COMMANDS[commandName] = method
                    }
                }
                jda.updateCommands().addCommands(commands).queue()
            }
        }

        private fun processCommandAnnotations(
            commandArguments: List<Argument>,
            commandAnnotation: Command,
            method: Method
        ): Pair<String, CommandData> {
            val optionDatas = ArrayList<OptionData>()

            if (commandArguments.isNotEmpty()) {
                for (argument in commandArguments) {
                    val optionData = AnnotationUtil.getOptionData(argument)
                    optionDatas.add(optionData)
                }
            }

            val commandName: String = commandAnnotation.name.lowercase().ifEmpty {
                method.name.lowercase()
            }
            val commandDescription = commandAnnotation.description
            val commandData = CommandData(commandName, commandDescription)
            commandData.addOptions(optionDatas)
            return Pair(commandName, commandData)
        }

        fun start() {
            startJDA()
        }

        private fun startJDA() {
            val jda = JDABuilder.createDefault(PropertiesUtil.properties["discord.token"] as String?)
                .addEventListeners(ReadyListener())
                .addEventListeners(MessageListener())
                .build()

            initAvailableCommands("com.softawii.ticketbot.command", jda)

            jda.awaitReady()
        }
    }
}