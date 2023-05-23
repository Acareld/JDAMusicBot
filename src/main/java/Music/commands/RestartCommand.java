package Music.commands;

import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import DiscordBot.Bot;
import Music.MusicController;
import Music.Queue;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class RestartCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message message) {
        Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
        
        Guild guild = c.getGuild();
        
        GuildVoiceState state;
        if ((state = m.getVoiceState()) != null) { 
            AudioChannel vc;
            if ((vc = state.getChannel()) != null) {
                MusicController controller = Bot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
                
                AudioManager manager = guild.getAudioManager();
                
                if(manager.isConnected()) {
                    AudioPlayer player = controller.getPlayer();
                    AudioTrack track = player.getPlayingTrack();
                    Queue queue = controller.getQueue();
                    
                    queue.addTrackToQueueNext(track.makeClone());
                    queue.next();
                } else {
                    c.sendMessage("bot is not connected to a vc").complete().delete().queueAfter(1500,
                            TimeUnit.MILLISECONDS);
                }
            } else {
                c.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500,
                        TimeUnit.MILLISECONDS);
            }
        } else {
            c.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500,
                    TimeUnit.MILLISECONDS);
        }
    
        message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
    }
    
    
    

}
