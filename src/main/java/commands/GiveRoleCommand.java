package commands;

import java.util.List;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class GiveRoleCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
		String[] args = message.getContentDisplay().split(" ");
		
		
		if(m.hasPermission(Permission.MANAGE_ROLES)) {
			
			if(args.length >= 2) {
				
				List<Member> members;
				if((members = message.getMentions().getMembers()) != null) {
					
					try {
						Role role;
						long id = Long.parseLong(args[1]);
						if((role = c.getGuild().getRoleById(id)) != null) {
							for(Member mentionedMember: members) {
								c.getGuild().addRoleToMember(mentionedMember, role).queue();
								c.sendMessage("added "+role.getName()+" to "+mentionedMember.getAsMention()+"").queue();

							}
						}
					} catch(NumberFormatException e) {
						e.printStackTrace();
					}
				} 	
			}
			
		}
		
	}

}
