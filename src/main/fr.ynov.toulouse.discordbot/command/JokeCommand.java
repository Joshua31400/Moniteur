package fr.ynov.toulouse.discordbot.command;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class JokeCommand implements ICommand {

    @Override
    public String getName() {
        return "joke";
    }

    @Override
    public String getDescription() {
        return "Affiche une blague aléatoire en mode détente";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        // Exécution asynchrone pour ne pas bloquer le bot pendant l'appel HTTP
        event.getChannel().sendTyping().queue();

        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://official-joke-api.appspot.com/random_joke"))
                .GET()
                .build();

        // Appel asynchrone
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    // Parsing du JSON avec Gson
                    JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                    String setup = json.get("setup").getAsString();
                    String punchline = json.get("punchline").getAsString();

                    event.getChannel().sendMessage("🎭 " + setup + "\n||" + punchline + "||").queue();
                })
                .exceptionally(e -> {
                    event.getChannel().sendMessage("⚠️ Erreur lors de la récupération de la blague (API inaccessible).").queue();
                    return null;
                });
    }
}