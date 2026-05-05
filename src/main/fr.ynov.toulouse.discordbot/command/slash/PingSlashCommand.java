package fr.ynov.toulouse.discordbot.command.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/** Version slash command de la commande ping. */
public class PingSlashCommand implements ISlashCommand {

    @Override
    public CommandData getCommandData() {
        return Commands.slash("ping", "Vérifie si le bot est en ligne et réactif.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Pong !").queue();
    }
}