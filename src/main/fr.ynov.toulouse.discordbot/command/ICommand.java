package fr.ynov.toulouse.discordbot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

// Interface pour appeller tout les commandes
public interface ICommand {
    String getName();
    String getDescription();
    void execute(MessageReceivedEvent event, String[] args);
}