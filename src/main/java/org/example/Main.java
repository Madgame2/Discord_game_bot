package org.example;

import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;


public class Main {
    public static void main(String[] args) {

        try{
            String token = System.getenv("GAME_BOT_TOKEN");
        JDABuilder bot1 = JDABuilder.createDefault(token);

        bot1.addEventListeners(new GameBot());

        bot1.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}