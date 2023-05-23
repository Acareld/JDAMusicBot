package Music.commands;

import java.util.concurrent.TimeUnit;
import java.awt.Color;

import DiscordBot.Bot;
import Music.MusicController;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class ShuffleCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();

		GuildVoiceState state;
		if ((state = m.getVoiceState()) != null) {
			AudioChannel vc;
			if ((vc = state.getChannel()) != null) {
				MusicController controller = Bot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
				controller.getQueue().shuffel();
				EmbedBuilder builder = new EmbedBuilder();
				builder.setTitle("Queue shuffled");
				builder.setColor(Color.decode("#1730eb"));
				c.sendMessageEmbeds(builder.build()).queue();
			}
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);

	}

}
