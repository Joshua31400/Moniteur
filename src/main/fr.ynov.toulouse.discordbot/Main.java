import fr.ynov.toulouse.discordbot.command.CommandManager;
import fr.ynov.toulouse.discordbot.command.HelpCommand;
import fr.ynov.toulouse.discordbot.command.JokeCommand;
import fr.ynov.toulouse.discordbot.command.PingCommand;
import fr.ynov.toulouse.discordbot.command.PollCommand;
import fr.ynov.toulouse.discordbot.command.RPSCommand;
import fr.ynov.toulouse.discordbot.command.RemindCommand;
import fr.ynov.toulouse.discordbot.command.slash.PingSlashCommand;
import fr.ynov.toulouse.discordbot.command.slash.SlashCommandManager;
import fr.ynov.toulouse.discordbot.listener.CommandListener;
import fr.ynov.toulouse.discordbot.listener.RPSListener;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

/**
 * Point d'entrée du bot Discord.
 * Charge la configuration, enregistre les commandes et démarre la connexion JDA.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        String token = loadToken();
        if (token == null) return;

        CommandManager commandManager = buildCommandManager();
        SlashCommandManager slashManager = buildSlashCommandManager();

        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(
                        new CommandListener(commandManager),
                        new RPSListener(),
                        slashManager
                )
                .build();

        jda.awaitReady();
        slashManager.pushCommandsToDiscord(jda);
        System.out.println("[BOT] Bot connecté et prêt.");
    }

    private static String loadToken() {
        String token = Dotenv.load().get("DISCORD_TOKEN");
        if (token == null) {
            System.err.println("[ERREUR] DISCORD_TOKEN introuvable dans le fichier .env");
        }
        return token;
    }

    private static CommandManager buildCommandManager() {
        CommandManager manager = new CommandManager();
        manager.registerCommand(new PingCommand());
        manager.registerCommand(new HelpCommand(manager));
        manager.registerCommand(new JokeCommand());
        manager.registerCommand(new PollCommand());
        manager.registerCommand(new RemindCommand());
        manager.registerCommand(new RPSCommand());
        return manager;
    }

    private static SlashCommandManager buildSlashCommandManager() {
        SlashCommandManager manager = new SlashCommandManager();
        manager.registerCommand(new PingSlashCommand());
        return manager;
    }
}