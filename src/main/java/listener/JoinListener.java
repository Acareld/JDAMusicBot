package listener;

import java.util.concurrent.TimeUnit;

import DiscordBot.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinListener extends ListenerAdapter {
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Member member = event.getMember();
		Guild guild = event.getGuild();
		Role role; 
		TextChannel botCh;
		if((role = Bot.INSTANCE.getRMan().getJoinRole(guild.getIdLong())) != null) {
			guild.addRoleToMember(member, role).queue();
		} else if((botCh = Bot.INSTANCE.getChMan().getBotChannel(guild.getIdLong())) != null) {
			botCh.sendMessage("no join role set for this guild").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
		}
	}

}
