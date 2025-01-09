package org.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.lang.reflect.Method;
import java.util.function.Function;

public class GameBot extends ListenerAdapter
{
    private comandsTable general = new comandsTable();

    public GameBot() {
        general.add("help","description", this,"help");
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        if (event.getAuthor().isBot()) return;

        // Ответ на команду "!hello" для первого бота
        if (event.getMessage().getMentionedUsers().contains(event.getJDA().getSelfUser())) {
            String rawContent = event.getMessage().getContentRaw();
            String contentWithoutMentions = rawContent.replaceAll("<@!?" + event.getJDA().getSelfUser().getId() + ">", "").trim();

            if(!contentWithoutMentions.isEmpty()){
                if(general.has(contentWithoutMentions)){
                    general.execute(contentWithoutMentions);
                }else{
                    event.getChannel().sendMessage("NO").queue();
                }

            }else{
                event.getChannel().sendMessage("Hello "+event.getMessage().getAuthor().getName()+" how i can help you\n if you nead help you can insert \"help\" and i will print you all my commands :3c").queue();
            }
        }
    }


    public void help(){

    }
}
