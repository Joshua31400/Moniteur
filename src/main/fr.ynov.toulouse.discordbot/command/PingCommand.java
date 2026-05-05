package fr.ynov.toulouse.discordbot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

// Verifier si le bot est bien actif
public class PingCommand implements ICommand {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Vérifie si le bot est en ligne et réactif.";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        // queue() est essentiel en JDA pour envoyer la requête à l'API de manière asynchrone
        event.getChannel().sendMessage("Pong !").queue();
    }
}