import net.dv8tion.jda.api.JDABuilder
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class TicketBot {
    companion object {
        fun start() {
            val propertiesFile = Properties()
            val reader = Files.newBufferedReader(Path.of("./application.properties"))
            propertiesFile.load(reader)
            reader.close()

            val jda = JDABuilder.createDefault(propertiesFile["discordToken"] as String?)
                .addEventListeners(ReadyListener())
                .addEventListeners(MessageListener())
                .build()

            jda.upsertCommand("help", "Ajuda").queue();
            jda.upsertCommand("ping", "Pong").queue();
            jda.upsertCommand("invite", "Convite do bot").queue();
            jda.awaitReady()
        }
    }
}