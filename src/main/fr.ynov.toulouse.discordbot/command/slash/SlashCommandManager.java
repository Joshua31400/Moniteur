package fr.ynov.toulouse.discordbot.command.slash;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlashCommandManager extends ListenerAdapter {
    private final Map<String, ISlashCommand> commands = new HashMap<>();

    public void registerCommand(ISlashCommand command) {
        commands.put(command.getCommandData().getName(), command);
    }

    // Synchronise nos classes Java avec l'interface native de Discord
    public void pushCommandsToDiscord(JDA jda) {
        List<CommandData> commandDataList = new ArrayList<>();
        for (ISlashCommand cmd : commands.values()) {
            commandDataList.add(cmd.getCommandData());
        }

        jda.updateCommands().addCommands(commandDataList).queue();
        System.out.println("Commandes natives synchronisées avec l'API Discord !");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        ISlashCommand command = commands.get(event.getName());

        if (command != null) {
            command.execute(event);
        } else {
            // setEphemeral(true) rend le message visible uniquement par l'utilisateur !
            event.reply("⚠️ Commande non reconnue.").setEphemeral(true).queue();
        }
    }
}