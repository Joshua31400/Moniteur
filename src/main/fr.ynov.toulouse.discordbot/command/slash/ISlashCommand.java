package fr.ynov.toulouse.discordbot.command.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface ISlashCommand {
    // Fournit les métadonnées pour l'API Discord
    CommandData getCommandData();

    void execute(SlashCommandInteractionEvent event);
}