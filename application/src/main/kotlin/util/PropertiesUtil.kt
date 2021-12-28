package util

import listener.MessageListener
import org.apache.logging.log4j.LogManager
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class PropertiesUtil {
    companion object {
        val LOGGER = LogManager.getLogger(MessageListener::class.java)
        lateinit var properties: Properties

        init {
            properties = loadPropertiesFile()
        }

        fun loadPropertiesFile(): Properties {
            val propertiesPath = Paths.get(".${File.separator}application${File.separator}application.properties")
            LOGGER.info("Searching for properties file in: ${propertiesPath.toAbsolutePath().toString()}")
            val reader = Files.newBufferedReader(propertiesPath)
            reader.use {
                val propertiesFile = Properties()
                propertiesFile.load(reader)
                return propertiesFile
            }
        }
    }
}