package Music.commands;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import DiscordBot.Bot;
import Music.MusicController;
import Music.Queue;
import Music.TrackScheduler;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class StopCommand implements ServerCommand {

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
				TrackScheduler scheduler = controller.getScheduler();
				AudioPlayer player = controller.getPlayer();
				Queue queue = controller.getQueue();
				AudioChannel botCh = manager.getConnectedChannel();
				
				if(vc.equals(botCh)) {
					queue.clearQueueList();
					scheduler.setRepeat(false);
					player.setPaused(false);
					player.stopTrack();
					manager.closeAudioConnection();
				//	ActivityChanger.updateActivity();
	
					EmbedBuilder builder = new EmbedBuilder();
					builder.setTitle("Player stopped");
					builder.setColor(Color.decode("#ED1A1A"));
					c.sendMessageEmbeds(builder.build()).queue();
				} else {
					c.sendMessage("You must be in the same voicechannel as the bot").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
				}
				

				
			} else {
				c.sendMessage("You must be connected to a voicechannel").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
			}
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);

	}

}
