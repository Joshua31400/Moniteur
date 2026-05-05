package fr.ynov.toulouse.discordbot.command;

import fr.ynov.toulouse.discordbot.BotConfig;
import fr.ynov.toulouse.discordbot.util.EmbedHelper;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.Color;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Liste toutes les commandes disponibles.";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        StringBuilder description = new StringBuilder();

        // On récupère toutes les commandes enregistrées dynamiquement
        for (ICommand cmd : manager.getRegisteredCommands()) {
            description.append("**").append(BotConfig.PREFIX).append(cmd.getName()).append("** : ")
                    .append(cmd.getDescription()).append("\n");
        }

        MessageEmbed embed = EmbedHelper.createDefaultEmbed(
                "🛠️ Liste des commandes",
                description.toString(),
                Color.BLUE
        );
        event.getChannel().sendMessageEmbeds(embed).queue();
    }
}