package org.example;

//import net.dv8tion.jda.api.interactions.commands.Command;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;


public class comandsTable implements Iterable<comandsTable.Command> {
    private final Map<String,Command> commands = new  HashMap<String,Command>();

    public void add(String command, String description, Object targetInstance, String methodName,Class<?>... params){
        command = command.trim();

        Method method;
        try {
            method = targetInstance.getClass().getDeclaredMethod(methodName,params);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Function<Object[], Object> action = args -> {
            try {
                return method.invoke(targetInstance, args); // Передаем аргументы в метод
            } catch (Exception e) {
                throw new RuntimeException("Failed to execute method", e);
            }
        };

        commands.put(command, new Command(command,description,action));
    }

    public Object execute(String command,Object... args){
        Function<Object[], Object> action = commands.get(command).action;
        if(action!=null){
            return action.apply(args);
        }else{
            throw new IllegalArgumentException("no such method");
        }
    }

    public boolean has(String comand){
        return commands.get(comand)!=null;
    }

    // Получить команду по ID
    private Command getById(int id) {
        int i = 0;
        for (Command command : commands.values()) {
            if (i == id) {
                return command;
            }
            i++;
        }
        throw new NoSuchElementException("No command with ID: " + id);
    }

    @Override
    public Iterator<Command> iterator() {
        return commands.values().iterator();
    }

    public class Command {
        private final String command;
        private final String description;
        private final Function<Object[], Object> action;

        public Command(String command, String description, Function<Object[], Object> action) {
            this.command = command;
            this.description = description;
            this.action = action;
        }

        public String getCommand() {
            return command;
        }

        public String getDescription() {
            return description;
        }

        public Object execute(Object... args) {
            return action.apply(args);
        }
    }
}
