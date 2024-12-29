package org.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GameBot extends ListenerAdapter
{
    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        if (event.getAuthor().isBot()) return;

        // Ответ на команду "!hello" для первого бота
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!hello")) {
            event.getChannel().sendMessage("Hello from Bot 1!").queue();
        }
    }
}
