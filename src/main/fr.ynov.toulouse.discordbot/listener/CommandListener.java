package fr.ynov.toulouse.discordbot.listener;

import fr.ynov.toulouse.discordbot.command.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Ecoute les messages discorde
public class CommandListener extends ListenerAdapter {
    private final CommandManager manager;

    public CommandListener(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Ignorer les messages des autres bots
        if (event.getAuthor().isBot()) return;

        manager.handle(event);
    }
}