package fr.ynov.toulouse.discordbot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import java.awt.Color;

public class EmbedHelper {
// Methode static pour gérer les templates avec un patern
    public static MessageEmbed createDefaultEmbed(String title, String description, Color color) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setDescription(description);
        builder.setColor(color);
        builder.setFooter("Bot Joshua - Ynov Toulouse");
        return builder.build();
    }
}