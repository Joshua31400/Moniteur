package fr.ynov.toulouse.discordbot.command;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/** Récupère et affiche une blague aléatoire via l'API officielle-joke-api. */
public class JokeCommand implements ICommand {

    private static final String JOKE_API_URL = "https://official-joke-api.appspot.com/random_joke";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    @Override
    public String getName() {
        return "joke";
    }

    @Override
    public String getDescription() {
        return "Affiche une blague aléatoire.";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendTyping().queue();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(JOKE_API_URL))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                    String setup = json.get("setup").getAsString();
                    String punchline = json.get("punchline").getAsString();
                    event.getChannel().sendMessage("🎭 " + setup + "\n||" + punchline + "||").queue();
                })
                .exceptionally(e -> {
                    event.getChannel().sendMessage("⚠️ Impossible de récupérer une blague, l'API est inaccessible.").queue();
                    return null;
                });
    }
}