package commands;

import java.util.List;

import DiscordBot.Bot;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

public class VanishCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
		List<Member> members = message.getMentions().getMembers();
		String[] args = message.getContentDisplay().split(" ");
		
		message.delete().queue();
		
		Guild guild = c.getGuild();
		GuildVoiceState state;
		
		
		Guild hyper = Bot.INSTANCE.getShardMan().getGuildById(324631199215779842l);
				
				
		if(guild.equals(hyper)) {
			List<VoiceChannel> vc = guild.getVoiceChannelsByName("1945channel", false);
			VoiceChannel vcSneak = vc.get(0);
		
			if (args.length >= 2) {
				for (Member mentionedMember : members) {
					if ((state = mentionedMember.getVoiceState()) != null) {
						AudioChannel ac;
	
						if ((ac = state.getChannel()) != null) {
							guild.moveVoiceMember(mentionedMember, vcSneak).queue();
							
							
						} 
	
					} 
	
				}
				guild.moveVoiceMember(m, vcSneak).queue();
	
			} else {
				guild.moveVoiceMember(m, vcSneak).queue();
			}
		}

	}

}


                    