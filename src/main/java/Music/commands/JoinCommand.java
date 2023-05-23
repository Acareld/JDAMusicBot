package Music.commands;

import java.util.concurrent.TimeUnit;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
		message.addReaction(emoji).queue();

		GuildVoiceState state;
		if ((state = m.getVoiceState()) != null) {
			AudioChannel vc;
			if ((vc = state.getChannel()) != null) {
				AudioManager manager = vc.getGuild().getAudioManager();
				manager.openAudioConnection(vc);
			}
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);

	}

}
