package Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import DiscordBot.Bot;
import net.dv8tion.jda.api.entities.Guild;

public class MusicController {

	private Guild guild;
	private AudioPlayer player;
	private TrackScheduler scheduler;
	private Queue queue;

	public MusicController(Guild guild) {
		this.guild = guild;
		this.player = Bot.INSTANCE.audioPlayerManager.createPlayer();
		this.queue = new Queue(this);

		this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
		this.scheduler = new TrackScheduler();
		this.player.addListener(scheduler);
		this.player.setVolume(15);
	}

	public Guild getGuild() {
		return guild;
	}

	public AudioPlayer getPlayer() {
		return player;
	}

	public Queue getQueue() {
		return queue;
	}
	
	public TrackScheduler getScheduler() {
        return scheduler;
    }

}
