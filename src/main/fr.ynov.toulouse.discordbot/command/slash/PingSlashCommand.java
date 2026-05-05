package fr.ynov.toulouse.discordbot.command.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class PingSlashCommand implements ISlashCommand {

    @Override
    public CommandData getCommandData() {
        return Commands.slash("ping", "Vérifie si le bot est en ligne et réactif.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        // Pour les Slash Commands, on doit impérativement utiliser reply() pour accuser réception
        event.reply("Pong ! (Système natif)").queue();
    }
}