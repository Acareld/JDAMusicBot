package DiscordBot;

import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

public class ChannelRunnable implements Runnable {

    
    VoiceChannel vc;
    
    
    public ChannelRunnable(VoiceChannel vc) {
        this.vc = vc;
    }
    
    @Override
    public void run() {
        while(vc.getMembers().size() > 0);
        
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vc.delete().queue();
        
        
    }

}
