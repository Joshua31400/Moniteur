package fr.ynov.toulouse.discordbot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Planifie l'envoi d'un message de rappel après un délai en secondes. */
public class RemindCommand implements ICommand {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public String getName() {
        return "remind";
    }

    @Override
    public String getDescription() {
        return "Envoie un rappel après X secondes. Usage : !remind <secondes> <message>";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (args.length < 2) {
            event.getChannel().sendMessage("⚠️ Usage incorrect. Exemple : `!remind 10 Vérifier les backups`").queue();
            return;
        }

        try {
            int seconds = Integer.parseInt(args[0]);
            String reminder = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            event.getChannel().sendMessage("⏱️ Rappel programmé dans " + seconds + " seconde(s).").queue();

            scheduler.schedule(() ->
                            event.getChannel().sendMessage(
                                    "🔔 <@" + event.getAuthor().getId() + "> Rappel : " + reminder
                            ).queue(),
                    seconds, TimeUnit.SECONDS
            );

        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("⚠️ Le délai doit être un nombre entier de secondes.").queue();
        }
    }
}