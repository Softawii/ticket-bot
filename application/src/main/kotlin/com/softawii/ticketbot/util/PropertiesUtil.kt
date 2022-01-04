package com.softawii.ticketbot.util

import com.softawii.ticketbot.listener.MessageListener
import org.apache.logging.log4j.LogManager
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.system.exitProcess

class PropertiesUtil {
    companion object {
        val LOGGER = LogManager.getLogger(MessageListener::class.java)
        var properties: Properties

        init {
            properties = loadPropertiesFile()
        }

        /**
         * Tries to find the file "application.properties" in the current folder,
         * in the parent folder or in the application folder, if exists.
         */
        fun loadPropertiesFile(): Properties {
            val currentFolder = Paths.get("").toAbsolutePath()
            val propertiesPath: Path
            if (Files.exists(currentFolder.resolve("application.properties"))) {
                propertiesPath = currentFolder.resolve("application.properties")
            } else {
                val applicationFolder = currentFolder.resolve("application/")
                val parentFolder = currentFolder.parent
                if (Files.exists(applicationFolder)) {
                    if (Files.exists(applicationFolder.resolve("application.properties"))) {
                        propertiesPath = applicationFolder.resolve("application.properties")
                    } else {
                        LOGGER.fatal("Unable to find the file \"application.properties\"")
                        exitProcess(-1)
                    }
                } else if (Files.exists(parentFolder.resolve("application.properties"))) {
                    propertiesPath = parentFolder.resolve("application.properties")
                } else {
                    LOGGER.fatal("Unable to find the file \"application.properties\"")
                    exitProcess(-1)
                }
            }
            LOGGER.info("Searching for properties file in: ${propertiesPath.toAbsolutePath()}")
            val reader = Files.newBufferedReader(propertiesPath)
            reader.use {
                val propertiesFile = Properties()
                propertiesFile.load(reader)
                return propertiesFile
            }
        }
    }
}