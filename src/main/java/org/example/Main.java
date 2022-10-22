package org.example;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.SelectMenuInteractionEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.SelectMenu;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.MessageCreateSpec;
import org.example.ffxivApi.FFXIVApi;
import org.example.ffxivApi.models.Character;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        DiscordClient client = DiscordClient.create(JSONFile.getJSONValueFromFile("discrodAPIKey", "keys.json"));
        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
            Mono<Void> printOnLogin = createPrintOnLogin(gateway);
            Mono<Void> handlePingCommand = createPingCommand(gateway);
            Mono<Void> handleIAM = createIAMCommand(gateway, client);


            return printOnLogin.and(handlePingCommand).and(handleIAM);
        });
        login.block();
    }

    private static Mono<Void> createPrintOnLogin(GatewayDiscordClient gateway) {
        return gateway.on(ReadyEvent.class, event ->
                Mono.fromRunnable(() -> {
                    final User self = event.getSelf();
                    System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                })).then();
    }

    private static Mono<Void> createPingCommand(GatewayDiscordClient gateway) {
        return gateway.on(MessageCreateEvent.class, event -> {
            Message message = event.getMessage();
            Optional<User> userOptional = message.getAuthor();
            if(userOptional.isPresent()){
                User user = userOptional.get();
                System.out.println(user.getUsername() + ": " + message.getContent());
            }

            if (message.getContent().equals("!ping"))
                return message.getChannel().flatMap(channel -> channel.createMessage("pong!"));

            return Mono.empty();
        }).then();
    }
    private static Mono<Void> createIAMCommand(GatewayDiscordClient gateway, DiscordClient client) {
        return gateway.on(MessageCreateEvent.class, event -> {
            Message message = event.getMessage();
            Optional<User> userOptional = message.getAuthor();
            User user = null;
            if(userOptional.isPresent()){
                user = userOptional.get();
                System.out.println(user.getUsername() + ": " + message.getContent());
            }


            if (message.getContent().startsWith("!setUser") && user != null) {
                String messageContent = message.getContent().substring("!setUser".length()).trim();
                String name = messageContent.substring(0, messageContent.lastIndexOf(" "));
                String server = messageContent.substring(messageContent.lastIndexOf(" ") + 1);
                List<Character> characterList = FFXIVApi.getInstance().getCharacters(name, server);

                String id = characterList.stream().map(Character::getId).findFirst().orElse(0l) + "";
//                List<SelectMenu.Option> options = ;
                SelectMenu select = SelectMenu.of("characters-" + user.getUsername(), characterList.subList(0,10).stream().map(ch -> SelectMenu.Option.of(ch.getName(), ch.getId() + "")).toList());
                return message.getChannel().flatMap(channel -> {
//                    return channel.createMessage("Your FFXIV ID is: " + id);
                    Mono<Message> createMessageMono = channel.createMessage(MessageCreateSpec.builder()
//                                    .content("test")
                            .addComponent(ActionRow.of(select))
                            .build());
//
                    Mono<Void> tempListener = gateway.on(SelectMenuInteractionEvent.class, e -> {
                                if (e.getCustomId().equals("custom-id")) {
                                    //Get all selected values
                                    String values = e.getValues().toString().replace("[", "").replace("]", "");
                                    return e.reply("You selected these values: " + values).withEphemeral(true);
                                } else {
                                    // Ignore it
                                    return Mono.empty();
                                }
                            }).timeout(Duration.ofMinutes(30)) // Timeout after 30 minutes
                            // Handle TimeoutException that will be thrown when the above times out
                            .onErrorResume(TimeoutException.class, ignore -> Mono.empty())
                            .then(); //Transform the flux to a mono

                    //Return both of the monos together
                    return createMessageMono.then(tempListener);
//                    channel.createMessage("Your FFXIV ID is: " + id);
                });

//                return message.getChannel().flatMap(channel -> channel.createMessage("Your FFXIV ID is: " + id));
            }

            return Mono.empty();
        }).then();
    }
}