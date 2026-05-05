package fr.ynov.toulouse.discordbot;

import fr.ynov.toulouse.discordbot.command.CommandManager;
import fr.ynov.toulouse.discordbot.command.PingCommand;
import fr.ynov.toulouse.discordbot.command.HelpCommand;
import fr.ynov.toulouse.discordbot.command.JokeCommand;
import fr.ynov.toulouse.discordbot.command.PollCommand;
import fr.ynov.toulouse.discordbot.listener.CommandListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");

        if (token == null) {
            System.err.println("Erreur: DISCORD_TOKEN introuvable dans le fichier .env");
            return;
        }

        try {
            CommandManager commandManager = new CommandManager();
            commandManager.registerCommand(new PingCommand());
            commandManager.registerCommand(new HelpCommand(commandManager));
            commandManager.registerCommand(new JokeCommand());
            commandManager.registerCommand(new PollCommand());

            // Initialisation du bot en ajoutant notre Listener
            JDA jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new CommandListener(commandManager))
                    .build();

            jda.awaitReady();
            System.out.println("Le bot Ops est en ligne sur Discord !");

        } catch (InterruptedException e) {
            System.err.println("⚠️ Erreur lors de la connexion du bot.");
            e.printStackTrace();
        }
    }
}