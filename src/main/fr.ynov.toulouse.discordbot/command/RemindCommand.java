package fr.ynov.toulouse.discordbot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Commande pour l'historique des messages
public class RemindCommand implements ICommand {

    // Le moteur qui va gérer nos tâches planifiées
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public String getName() {
        return "remind";
    }

    @Override
    public String getDescription() {
        return "Bot envoie un message après X secondes. Usage: !remind <temps> <message>";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (args.length < 2) {
            event.getChannel().sendMessage("⚠️ Usage incorrect. Exemple : `!remind 10 Vérifier les backups`").queue();
            return;
        }

        try {
            int seconds = Integer.parseInt(args[0]);

            // On récupère le reste des arguments pour reformer le message
            String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            event.getChannel().sendMessage("⏱️ C'est noté ! Rappel programmé dans " + seconds + " secondes.").queue();

            // On planifie l'envoi du message de rappel
            scheduler.schedule(() -> {
                event.getChannel().sendMessage("🔔 <@" + event.getAuthor().getId() + "> Rappel : " + message).queue();
            }, seconds, TimeUnit.SECONDS);

        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("⚠️ Le temps doit être un nombre entier de secondes.").queue();
        }
    }
}