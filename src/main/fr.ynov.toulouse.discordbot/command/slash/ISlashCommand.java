package fr.ynov.toulouse.discordbot.command.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * Contrat que toute slash command doit respecter.
 */
public interface ISlashCommand {

    /** @return les métadonnées envoyées à l'API Discord lors de la synchronisation. */
    CommandData getCommandData();

    /** Exécute la slash command lors de son déclenchement. */
    void execute(SlashCommandInteractionEvent event);
}