package Music.commands;

import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import DiscordBot.Bot;
import Music.MusicController;
import Music.TrackScheduler;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class SkipCommand implements ServerCommand {
	public static boolean isSkipped = false;

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();

		GuildVoiceState state;
		if ((state = m.getVoiceState()) != null) {
			AudioChannel vc;
			if ((vc = state.getChannel()) != null) {
				MusicController controller = Bot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
				AudioPlayer player = controller.getPlayer();

				if (player.getPlayingTrack() == null) {
					c.sendMessage("there is no track playing currently").complete().delete().queueAfter(1500,
							TimeUnit.MILLISECONDS);
					return;
				} else {
				    TrackScheduler scheduler = controller.getScheduler();
				    scheduler.setRepeat(false);
					if (controller.getQueue().getQueuelist().size() >= 1) {
						isSkipped = true;
						controller.getQueue().next();
						isSkipped = false;
					} else {
						c.sendMessage("there is no next track").complete().delete().queueAfter(1500,
								TimeUnit.MILLISECONDS);
					}
				}

			}
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
	}

	public static boolean getSkipped() {
		return isSkipped;
	}

}
