package commands;

import java.io.File;
import java.util.concurrent.TimeUnit;

import DiscordBot.Bot;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.utils.FileUpload;


public class GetLogFileCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message message) {
        Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
        
        if(m.hasPermission(Permission.MANAGE_SERVER)) {
            User user = m.getUser();
            String path = Bot.getOnlineLogPath();
            File file = new File(path);
            FileUpload up = FileUpload.fromData(file);
           
            user.openPrivateChannel().flatMap(ch -> ch.sendFiles(up)).queue();
            c.sendMessage(user.getAsMention() + ", Online Log file: ").queue();
            c.sendFiles(up).queue();
            
            
            
        } else {
            c.sendMessage("you do not have the rights to do that").complete().delete().queueAfter(1500,
                    TimeUnit.MILLISECONDS);
        }
        message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
    }

}
