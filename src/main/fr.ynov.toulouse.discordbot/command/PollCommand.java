package fr.ynov.toulouse.discordbot.command;

import fr.ynov.toulouse.discordbot.util.EmbedHelper;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;

/** Crée un sondage interactif avec réactions Discord. */
public class PollCommand implements ICommand {

    private static final String[] EMOJIS = {"🇦", "🇧", "🇨", "🇩", "🇪"};

    @Override
    public String getName() {
        return "poll";
    }

    @Override
    public String getDescription() {
        return "Crée un sondage. Usage : !poll <question> | <option1> | <option2>";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (args.length == 0) {
            event.getChannel().sendMessage("⚠️ Usage incorrect. Exemple : `!poll Pizza ce midi ? | Oui | Non`").queue();
            return;
        }

        String[] parts = String.join(" ", args).split("\\|");

        if (parts.length < 3 || parts.length > 6) {
            event.getChannel().sendMessage("⚠️ Il faut une question et entre 2 et 5 options, séparées par `|`.").queue();
            return;
        }

        String question = parts[0].trim();
        StringBuilder optionsText = new StringBuilder();
        for (int i = 1; i < parts.length; i++) {
            optionsText.append(EMOJIS[i - 1]).append(" ").append(parts[i].trim()).append("\n\n");
        }

        MessageEmbed embed = EmbedHelper.createDefaultEmbed("📊 Sondage : " + question, optionsText.toString(), Color.ORANGE);

        event.getChannel().sendMessageEmbeds(embed).queue(message -> {
            for (int i = 1; i < parts.length; i++) {
                message.addReaction(Emoji.fromUnicode(EMOJIS[i - 1])).queue();
            }
        });
    }
}