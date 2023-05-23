package Music.commands;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import DiscordBot.Bot;
import Music.AudioLoadResult;
import Music.MusicController;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayNowCommand implements ServerCommand {
	public static boolean isPlayNow = false;

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {

		String[] args = message.getContentDisplay().split(" ");

		if (args.length > 1) {
			GuildVoiceState state;
			if ((state = m.getVoiceState()) != null) {
				AudioChannel vc;
				if ((vc = state.getChannel()) != null) {
					AudioManager manager = vc.getGuild().getAudioManager();
					manager.openAudioConnection(vc);
					MusicController controller = Bot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
					AudioPlayerManager apm = Bot.INSTANCE.audioPlayerManager;

					StringBuilder strBuilder = new StringBuilder();
					for (int i = 1; i < args.length; i++)
						strBuilder.append(args[i] + " ");

					String url = strBuilder.toString().trim();
					if (!url.startsWith("http")) {
						url = "ytsearch: " + url;
					}
					isPlayNow = true;
					apm.loadItem(url, new AudioLoadResult(controller, url));

				} else {
					c.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500,
							TimeUnit.MILLISECONDS);
				}
			} else {
				c.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500,
						TimeUnit.MILLISECONDS);
			}
		} else {
			EmbedBuilder builder = new EmbedBuilder();
			builder.setDescription("Please use !play <url/search query>");
			builder.setColor(Color.decode("#f22613"));
			c.sendMessageEmbeds(builder.build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
		}
	}

	public static boolean getIsPlayNow() {
		return isPlayNow;
	}

	public static void reset() {
		isPlayNow = false;
	}
}
