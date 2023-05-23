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

public class ResumeCommand implements ServerCommand {

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
				AudioManager manager = vc.getGuild().getAudioManager();
				
				if(manager.getConnectedChannel() != null) {
					if (player.isPaused()) {
	
						player.setPaused(false);
				//		ActivityChanger.updateActivity();
						EmbedBuilder builder = new EmbedBuilder();
						builder.setDescription("Player is now playing again");
						builder.setColor(Color.decode("#1730eb"));
						c.sendMessageEmbeds(builder.build()).queue();
					} else {
						c.sendMessage("Player is already playing").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
					}
				} else {
					c.sendMessage("Bot is not connected to a vc").complete().delete().queueAfter(1500,
							TimeUnit.MILLISECONDS);
				}
			} else {
				c.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500,
						TimeUnit.MILLISECONDS);
			}
		} else {
			c.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);

	}

}
