package Music.commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import DiscordBot.Bot;
import Music.MusicController;
import Music.Queue;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.managers.AudioManager;

public class RemoveLastCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message message) {
        Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
        
        GuildVoiceState state;
        if ((state = m.getVoiceState()) != null) {
            AudioChannel vc;
            if ((vc = state.getChannel()) != null) {
                MusicController controller = Bot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
                AudioManager manager = vc.getGuild().getAudioManager();              
                Queue queue = controller.getQueue();
                AudioChannel botCh = manager.getConnectedChannel();
                List<AudioTrack> tracks = queue.getQueuelist();
                
                if(vc.equals(botCh)) {
                    if(tracks.size() >= 1) {
                        AudioTrack track = queue.removeLast();
                        AudioTrackInfo info = track.getInfo();
                        String title = info.title;
                        String author = info.author;                        
             
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setAuthor("Removed this track from the queue: ");
                        builder.setTitle(title);
                        builder.setDescription(author);
                        c.sendMessageEmbeds(builder.build()).queue();
                        
                    } else {
                        c.sendMessage("Queue is already empty.").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
                    }
                    
                    
                } else {
                    c.sendMessage("You must be in the same voicechannel as the bot.").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
                }
 
            } else {
                c.sendMessage("You must be connected to a voicechannel.").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
            }
        }
        message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
    }

}


