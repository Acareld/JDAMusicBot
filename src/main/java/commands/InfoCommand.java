package commands;

import java.util.List;

import DiscordBot.Bot;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class InfoCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
		List<Member> members = message.getMentions().getMembers();
		String[] args = message.getContentDisplay().split(" ");
		message.delete().queue();

		System.out.println("ChannelId: " + c.getIdLong());
		System.out.println("GuildId: " + m.getGuild().getIdLong());
		System.out.println("BotChannel: " + Bot.INSTANCE.getChMan().getBotChannel(c.getGuild().getIdLong()));

		if (args.length >= 2) {

			for (Member mentionedMember : members) {
				List<Role> roles = mentionedMember.getRoles();
				
				System.out.println(mentionedMember.getEffectiveName() + " id: " + mentionedMember.getIdLong());
				
				for (Role role : roles) {
					System.out.println(role.getName() + ": " + role.getIdLong());
				}
				
				System.out.println();
			}
		}
		
	}
}
