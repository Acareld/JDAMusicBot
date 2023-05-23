package commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class BlowCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
		
		List<Member> members = message.getMentions().getMembers();
		String[] args = message.getContentDisplay().split(" ");
		
		User author = message.getAuthor();
		
		if (args.length >= 2) {
			
			if(members.size() >= 1) {
				
				for(Member mentionedMember: members) {
					String blow = 						  
							  "` _____`\r\n"
							+ "`/  _/\\\\`\r\n"
							+ "`| / oo`\r\n"
							+ "`\\(   _\\`\r\n"
							+ "` \\  O/`\r\n"
							+ "` /   \\`\r\n"
							+ "` ||  ||`\r\n"
							+ "` ||  ||      \"Hmmmmmmmm... "+author.getName()+"\"`\r\n"
							+ "` ||  || _____ /`\r\n"
							+ "` | \\ ||(___  )`\r\n"
							+ "`// / \\|_)o (  )`\r\n"
							+ "`\\\\ ///|)\\_(    )`\r\n"
							+ "` ||   |\\ )(    )	<-- "+mentionedMember.getEffectiveName()+"`\r\n"
							+ "` ||   ) \\/(____)_     ___`\r\n"
							+ "` ||   |\\___/     '---' '.'.`\r\n"
							+ "` ||   | :   _       .'   ) )`\r\n"
							+ "` ||   | '..' '~~~-.'   .'__ _`\r\n"
							+ "` \\\\  /           '.______  ( (`\r\n"
							+ "` ((___ooO                '._\\_\\`";
					c.sendMessage(blow).queue();				
				}
			}  else {
				String arg = "";
				
				for(int i = 1;i < args.length;i++) {
					arg = arg + args[i] + " ";
				}
				String blow = 						  
						  "` _____`\r\n"
						+ "`/  _/\\\\`\r\n"
						+ "`| / oo`\r\n"
						+ "`\\(   _\\`\r\n"
						+ "` \\  O/`\r\n"
						+ "` /   \\`\r\n"
						+ "` ||  ||`\r\n"
						+ "` ||  ||      \"Hmmmmmmmm... "+author.getName()+"\"`\r\n"
						+ "` ||  || _____ /`\r\n"
						+ "` | \\ ||(___  )`\r\n"
						+ "`// / \\|_)o (  )`\r\n"
						+ "`\\\\ ///|)\\_(    )`\r\n"
						+ "` ||   |\\ )(    )	<-- "+arg+"`\r\n"
						+ "` ||   ) \\/(____)_     ___`\r\n"
						+ "` ||   |\\___/     '---' '.'.`\r\n"
						+ "` ||   | :   _       .'   ) )`\r\n"
						+ "` ||   | '..' '~~~-.'   .'__ _`\r\n"
						+ "` \\\\  /           '.______  ( (`\r\n"
						+ "` ((___ooO                '._\\_\\`";
				c.sendMessage(blow).queue();
			}
		} else {
			c.sendMessage("state someone to blow you").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
		}
		
		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
	}

}
