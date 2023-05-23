package commands;

import java.util.concurrent.TimeUnit;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class EncodeCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message message) {
        String[] args = message.getContentDisplay().substring(1).split(" ");
        String[] input = message.getContentDisplay().substring(1).split("\"");
        
        
        if(input.length >= 2) {
            if(args[1].equalsIgnoreCase("ascii"))  {
                
            } else if(args[1].equalsIgnoreCase("binary")) {
                
            } else if(args[1].equalsIgnoreCase("hex")) {
                
            } else {
                c.sendMessage("unknown encoding format").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
            }
            
        } else {
            c.sendMessage("no message was given to encode").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
        }
        message.delete().queue();
    }

}
