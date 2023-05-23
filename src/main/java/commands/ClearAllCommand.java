package commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ClearAllCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
		Guild guild = m.getGuild();
		List<TextChannel> botChannels = guild.getTextChannelsByName("bot-abfall", false);
		TextChannel botCh = botChannels.get(0);

		if (m.hasPermission(Permission.KICK_MEMBERS)) {
			botCh.createCopy().queue();
			botCh.delete().queue();
		} else {
			c.sendMessage("you do not have the rights to do that").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
		}

	}

}
