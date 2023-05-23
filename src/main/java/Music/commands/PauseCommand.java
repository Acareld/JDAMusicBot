package Music.commands;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import DiscordBot.Bot;
import Music.ActivityChanger;
import Music.MusicController;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PauseCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();

		GuildVoiceState state;
		if ((state = m.getVoiceState()) != null) {
			AudioChannel vc;
			if ((vc = state.getChannel()) != null) {

				MusicController controller = Bot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
				AudioManager manager = vc.getGuild().getAudioManager();
				AudioPlayer player = controller.getPlayer();
				
				if(manager.getConnectedChannel() != null) {
					if (!player.isPaused()) {
	
						player.setPaused(true);
					//	ActivityChanger.updateActivity();
						EmbedBuilder builder = new EmbedBuilder();
						builder.setDescription("Player is now paused");
						builder.setColor(Color.decode("#1730eb"));
						channel.sendMessageEmbeds(builder.build()).queue();
					} else {
						channel.sendMessage("Player is already paused").complete().delete().queueAfter(1500,
								TimeUnit.MILLISECONDS);
					}
				} else {
					channel.sendMessage("Bot is not connected to a vc").complete().delete().queueAfter(1500,
							TimeUnit.MILLISECONDS);
				}
			} else {
				channel.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500,
						TimeUnit.MILLISECONDS);
			}
		} else {
			channel.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500,
					TimeUnit.MILLISECONDS);
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);

	}

}
