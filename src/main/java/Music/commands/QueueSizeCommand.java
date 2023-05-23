package Music.commands;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import DiscordBot.Bot;
import Music.MusicController;

import Music.Queue;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class QueueSizeCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();

		GuildVoiceState state;
		if ((state = m.getVoiceState()) != null) {
			AudioChannel vc;

			if ((vc = state.getChannel()) != null) {
				MusicController controller = Bot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
				Queue queue = controller.getQueue();

				EmbedBuilder builder = new EmbedBuilder();
				builder.setDescription(queue.getQueueSize() + " tracks in queue");
				builder.setColor(Color.decode("#1730eb"));
				c.sendMessageEmbeds(builder.build()).queue();

			}
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);

	}

}
