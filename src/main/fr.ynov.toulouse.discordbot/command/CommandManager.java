package fr.ynov.toulouse.discordbot.command;

import fr.ynov.toulouse.discordbot.BotConfig;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Routeur central des commandes préfixées.
 * Enregistre les commandes et dispatche chaque message entrant vers la bonne.
 */
public class CommandManager {

    private static final DateTimeFormatter TIMESTAMP_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Map<String, ICommand> commands = new HashMap<>();

    /** Enregistre une commande en l'indexant par son nom. */
    public void registerCommand(ICommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    /** Retourne toutes les commandes enregistrées (utilisé par {@code !help}). */
    public Collection<ICommand> getRegisteredCommands() {
        return commands.values();
    }

    /**
     * Parse le message reçu et dispatche vers la commande correspondante.
     * Ignore les messages qui ne commencent pas par le préfixe.
     */
    public void handle(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (!message.startsWith(BotConfig.PREFIX)) return;

        log(event.getAuthor().getName(), message);

        String[] split = message.replaceFirst(BotConfig.PREFIX, "").split("\\s+");
        String commandName = split[0].toLowerCase();
        String[] args = Arrays.copyOfRange(split, 1, split.length);

        ICommand command = commands.get(commandName);
        if (command != null) {
            command.execute(event, args);
        } else {
            event.getChannel().sendMessage("⚠️ Commande inconnue. Tapez `!help` pour voir la liste.").queue();
        }
    }

    private void log(String author, String message) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);
        System.out.println("[" + timestamp + "] " + author + " → " + message);
    }
}