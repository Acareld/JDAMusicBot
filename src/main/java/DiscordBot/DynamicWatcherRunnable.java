package DiscordBot;

import java.util.List;

import manage.ChannelManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;

public class DynamicWatcherRunnable implements Runnable {

    VoiceChannel vc;
    Category c;
    
    public DynamicWatcherRunnable(VoiceChannel vc, Category c) {
        this.vc = vc;
        this.c = c;
    }
    
    @Override
    public void run() {
        ChannelManager manager = Bot.INSTANCE.getChMan();
        Guild guild = vc.getGuild();
        
        while(!Thread.currentThread().isInterrupted()) {
            List<Member> member = vc.getMembers();
            if(member.size() > 0) {
                switch(manager.dynamicVc) {
                case 0:
                    
                    guild.createVoiceChannel("DynamicChannel_1", c).queue();
                    guild.moveVoiceMember(member.get(0), guild.getVoiceChannelsByName("DynamicChannel_1", true).get(0)).queue();;
                    
                    Thread t = new Thread(new ChannelRunnable(guild.getVoiceChannelById("DynamicChannel_1")));
                    t.start();
                    break;
                    
                case 1:
                    guild.createVoiceChannel("DynamicChannel_2", c).queue();
                    guild.moveVoiceMember(member.get(0), guild.getVoiceChannelById("DynamicChannel_2")).queue();;
                    
                    Thread t1 = new Thread(new ChannelRunnable(guild.getVoiceChannelById("DynamicChannel_2")));
                    t1.start();
                    break;
                    
                case 2:
                    guild.createVoiceChannel("DynamicChannel_3", c).queue();
                    guild.moveVoiceMember(member.get(0), guild.getVoiceChannelById("DynamicChannel_3")).queue();;
                    
                    Thread t2 = new Thread(new ChannelRunnable(guild.getVoiceChannelById("DynamicChannel_3")));
                    t2.start();
                    break;
                    
                case 3:
                    guild.createVoiceChannel("DynamicChannel_4", c).queue();
                    guild.moveVoiceMember(member.get(0), guild.getVoiceChannelById("DynamicChannel_4")).queue();;
                    
                    Thread t3 = new Thread(new ChannelRunnable(guild.getVoiceChannelById("DynamicChannel_4")));
                    t3.start();
                    break;
                    
                case 4:
                    Bot.INSTANCE.getChMan().getBotChannel(guild.getIdLong()).sendMessage("There are already 4 Dynamic Channels!").queue();
                    break;
                }
            
            
            }
        
        }
    }

}
