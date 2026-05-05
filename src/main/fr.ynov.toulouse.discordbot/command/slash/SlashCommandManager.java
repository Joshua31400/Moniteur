package fr.ynov.toulouse.discordbot.command.slash;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Routeur des slash commands natives Discord.
 * Gère l'enregistrement, la synchronisation avec l'API et le dispatch des interactions.
 */
public class SlashCommandManager extends ListenerAdapter {

    private final Map<String, ISlashCommand> commands = new HashMap<>();

    /** Enregistre une slash command en l'indexant par son nom. */
    public void registerCommand(ISlashCommand command) {
        commands.put(command.getCommandData().getName(), command);
    }

    /**
     * Synchronise les commandes enregistrées avec l'API Discord.
     * À appeler une seule fois après {@code jda.awaitReady()}.
     */
    public void pushCommandsToDiscord(JDA jda) {
        List<CommandData> commandDataList = new ArrayList<>();
        for (ISlashCommand cmd : commands.values()) {
            commandDataList.add(cmd.getCommandData());
        }
        jda.updateCommands().addCommands(commandDataList).queue();
        System.out.println("[SLASH] Slash commands synchronisées avec l'API Discord.");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        ISlashCommand command = commands.get(event.getName());
        if (command != null) {
            command.execute(event);
        } else {
            event.reply("⚠️ Commande non reconnue.").setEphemeral(true).queue();
        }
    }
}