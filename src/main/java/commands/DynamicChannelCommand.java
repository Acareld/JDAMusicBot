package commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import DiscordBot.Bot;
import DiscordBot.DynamicWatcherRunnable;
import commands.types.ServerCommand;
import manage.ChannelManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.requests.RestAction;

public class DynamicChannelCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message message) {
        Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
        
        VoiceChannel vc;
        
        Guild guild = c.getGuild();
        ChannelManager manager = Bot.INSTANCE.getChMan();
        
        
        if (guild.getVoiceChannelsByName("DynamicChannel", true).size() == 0) {
            RestAction<Category> action = guild.createCategory("dynamic");
            action.queue();
            
            guild = c.getGuild();
            System.out.println(guild.getCategories());
            
            
            List<Category> categories = guild.getCategoriesByName("dynamic", true);
            
            guild.createVoiceChannel("DynamicChannel", guild.getCategoryById(1092196781544198245l)).queue();
            c.sendMessage("Dynamic Channel Splitter was created!").queue();
            
            Thread t = new Thread(new DynamicWatcherRunnable(guild.getVoiceChannelById(1092197216178938018l), guild.getCategoryById(1092196781544198245l)));
            t.start();
        } else {
            Thread t = new Thread(new DynamicWatcherRunnable(guild.getVoiceChannelById(1092197216178938018l), guild.getCategoryById(1092196781544198245l)));
            t.start();
            c.sendMessage("DynamicChannel was already created!").queue();
        }
        
        
        
        
        message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
        
    }

}
