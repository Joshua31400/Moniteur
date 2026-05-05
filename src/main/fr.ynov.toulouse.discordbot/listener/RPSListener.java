package fr.ynov.toulouse.discordbot.listener;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.HashMap;
import java.util.Map;

public class RPSListener extends ListenerAdapter {

    public static final Map<String, GameSession> activeGames = new HashMap<>();

    public static class GameSession {
        public String p1Id;
        public String p2Id;
        public String p1Choice = null;
        public String p2Choice = null;

        public GameSession(String p1Id, String p2Id) {
            this.p1Id = p1Id;
            this.p2Id = p2Id;
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getUser() == null || event.getUser().isBot()) return;

        GameSession game = activeGames.get(event.getMessageId());
        if (game == null) return;

        String userId = event.getUserId();
        if (!userId.equals(game.p1Id) && !userId.equals(game.p2Id)) return;

        String emojiName = event.getEmoji().getName();
        if (!emojiName.equals("🪨") && !emojiName.equals("📄") && !emojiName.equals("✂️")) return;

        if (userId.equals(game.p1Id)) game.p1Choice = emojiName;
        if (userId.equals(game.p2Id)) game.p2Choice = emojiName;

        if (game.p1Choice != null && game.p2Choice != null) {
            activeGames.remove(event.getMessageId());
            String result = determineWinner(game.p1Choice, game.p2Choice, game.p1Id, game.p2Id);

            event.getChannel().sendMessage("🏁 **Résultat du duel !**\n" +
                    "<@" + game.p1Id + "> a joué " + game.p1Choice + "\n" +
                    "<@" + game.p2Id + "> a joué " + game.p2Choice + "\n\n" +
                    "🏆 " + result).queue();
        }
    }

    private String determineWinner(String c1, String c2, String p1, String p2) {
        if (c1.equals(c2)) return "Égalité parfaite !";
        if ((c1.equals("🪨") && c2.equals("✂️")) ||
                (c1.equals("📄") && c2.equals("🪨")) ||
                (c1.equals("✂️") && c2.equals("📄"))) {
            return "<@" + p1 + "> remporte la victoire !";
        }
        return "<@" + p2 + "> remporte la victoire !";
    }
}