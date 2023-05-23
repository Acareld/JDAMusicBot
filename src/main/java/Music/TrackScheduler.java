package Music;

import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import DiscordBot.Bot;
import Music.commands.PlayNowCommand;
import Music.commands.SkipCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class TrackScheduler extends AudioEventAdapter {
    private boolean isRepeat = false;
    private boolean isRepeatQueue = false;
    
	@Override
	public void onPlayerPause(AudioPlayer player) {

	}

	@Override
	public void onPlayerResume(AudioPlayer player) {

	}

	@Override
	public void onTrackStart(AudioPlayer player, AudioTrack track) {
		
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
	    long guildid = Bot.INSTANCE.playerManager.getGuildByPlayerHash(player.hashCode());
		Guild guild = Bot.INSTANCE.shardMan.getGuildById(guildid);
		MusicController controller = Bot.INSTANCE.playerManager.getController(guildid);
		Queue queue = controller.getQueue();
		TextChannel botCh = Bot.INSTANCE.getChMan().getBotChannel(guildid);
		
		botCh.sendMessage(endReason.toString());		
		if (isRepeat) {
		    queue.addTrackToQueueNext(track.makeClone());
	        if(queue.next()) return;	        
        } 
            
            
		if (endReason.mayStartNext) {	   
		    if (queue.next())return;
		    
		} 
		if(endReason == AudioTrackEndReason.LOAD_FAILED) {
		    botCh.sendMessage("Load failed").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
		}
		if (!SkipCommand.getSkipped() && !PlayNowCommand.getIsPlayNow() && !isRepeat) {
			AudioManager manager = guild.getAudioManager();
			player.setPaused(false);
			player.stopTrack();
			SkipCommand.isSkipped = false;
            isRepeat = false;
			manager.closeAudioConnection();
			
		}
		PlayNowCommand.reset();
	//	ActivityChanger.updateActivity();

	}
	
	public void setRepeat(boolean repeat) {
	    isRepeat = repeat;
	}
	
	public boolean getRepeat() {
	    return this.isRepeat;
	}

}
