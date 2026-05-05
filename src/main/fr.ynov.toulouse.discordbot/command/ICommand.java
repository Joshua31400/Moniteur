package fr.ynov.toulouse.discordbot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Contrat que toute commande préfixée doit respecter.
 */
public interface ICommand {
    /** @return le nom de la commande (sans préfixe), en minuscules. */
    String getName();

    /** @return une courte description affichée dans {@code !help}. */
    String getDescription();

    /** Exécute la commande avec les arguments extraits du message. */
    void execute(MessageReceivedEvent event, String[] args);
}