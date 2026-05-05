package fr.ynov.toulouse.discordbot.util;

import fr.ynov.toulouse.discordbot.BotConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;

/**
 * Utilitaire pour la création d'embeds Discord avec un style uniforme.
 */
public class EmbedHelper {

    /**
     * Crée un embed avec le titre, la description et la couleur fournis.
     * Le footer est automatiquement défini depuis {@link BotConfig#BOT_FOOTER}.
     */
    public static MessageEmbed createDefaultEmbed(String title, String description, Color color) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(color)
                .setFooter(BotConfig.BOT_FOOTER)
                .build();
    }
}