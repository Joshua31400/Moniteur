package fr.ynov.toulouse.discordbot.command;

import fr.ynov.toulouse.discordbot.listener.RPSListener;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class RPSCommand implements ICommand {

    @Override
    public String getName() {
        return "rps";
    }

    @Override
    public String getDescription() {
        return "Pierre-feuille-ciseaux contre un membre. Usage: !rps @user";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        // On s'assure qu'un adversaire a bien été mentionné[cite: 1]
        if (event.getMessage().getMentions().getUsers().isEmpty()) {
            event.getChannel().sendMessage("⚠️ Il faut mentionner un adversaire ! Exemple : `!rps @Membre`").queue();
            return;
        }

        User opponent = event.getMessage().getMentions().getUsers().get(0);
        if (opponent.isBot()) {
            event.getChannel().sendMessage("⚠️ Tu ne peux pas jouer contre un bot !").queue();
            return;
        }

        event.getChannel().sendMessage("🎮 **Pierre-Feuille-Ciseaux**\n" +
                        "<@" + event.getAuthor().getId() + "> VS <@" + opponent.getId() + ">\n" +
                        "Cliquez sur une réaction pour jouer !")
                .queue(message -> {
                    message.addReaction(Emoji.fromUnicode("🪨")).queue();
                    message.addReaction(Emoji.fromUnicode("📄")).queue();
                    message.addReaction(Emoji.fromUnicode("✂️")).queue();

                    RPSListener.activeGames.put(message.getId(),
                            new RPSListener.GameSession(event.getAuthor().getId(), opponent.getId()));
                });
    }
}