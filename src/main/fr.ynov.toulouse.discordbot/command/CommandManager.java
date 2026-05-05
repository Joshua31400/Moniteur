package fr.ynov.toulouse.discordbot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import fr.ynov.toulouse.discordbot.BotConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

// Classe pour router tout les requêtes
public class CommandManager {
    private final Map<String, ICommand> commands = new HashMap<>();

    public void registerCommand(ICommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    // Parse le message et dispatch vers la commande
    public void handle(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (!message.startsWith(BotConfig.PREFIX)) return;

        String[] split = message.replaceFirst(BotConfig.PREFIX, "").split("\\s+");
        String commandName = split[0].toLowerCase();

        String[] args = Arrays.copyOfRange(split, 1, split.length);

        ICommand command = commands.get(commandName);

        if (command != null) {
            command.execute(event, args);
        } else {
            // Gestion des commandes inconnues
            event.getChannel().sendMessage("⚠️ Commande inconnue. Tapez `!help` pour voir la liste.").queue();
        }
    }

    public java.util.Collection<ICommand> getRegisteredCommands() {
        return commands.values();
    }
}