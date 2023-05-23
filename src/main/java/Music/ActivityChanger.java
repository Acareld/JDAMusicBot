package Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import DiscordBot.Bot;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.ShardManager;

public class ActivityChanger {

	public static void updateActivity() {
		MusicController controller = Bot.INSTANCE.playerManager.getController(324631199215779842l);
		AudioPlayer player = controller.getPlayer();
		ShardManager shardMan = Bot.INSTANCE.getShardMan();

		AudioTrack track;
		if(player.isPaused()) {
			AudioTrackInfo info = player.getPlayingTrack().getInfo();

			String activity = info.title;
			shardMan.getShards().forEach(jda -> {
				jda.getPresence().setActivity(Activity.playing("|PAUSED| "+ activity));
			});
			
		} else if((track = player.getPlayingTrack()) != null && !player.isPaused()) {
			AudioTrackInfo info = track.getInfo();

			String activity = info.title;

			shardMan.getShards().forEach(jda -> {
				jda.getPresence().setActivity(Activity.playing(activity));
			});

		} else {
			shardMan.getShards().forEach(jda -> {
				jda.getPresence().setActivity(Activity.competing("cotton picking"));
			});
		}
	}

}
