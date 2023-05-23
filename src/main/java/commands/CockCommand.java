package commands;

import java.util.concurrent.TimeUnit;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class CockCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
		
		String[] args = message.getContentDisplay().split(" ");
		
		if(args.length == 2) {
			try {
				
				int amount = Integer.parseInt(args[1]);
				if(amount < 50) {
					String shaft = 
							  "`         #######          `\r\n"
							+ "`        #   #   #         `\r\n"
							+ "`       #    #    #        `\r\n"
							+ "`       #         #        `\r\n"
							+ "`        #########         `\r\n"
							+ "`         #######          `\r\n";
					
					
					for(int i = 0;i<amount;i++) {
						shaft = shaft + "`         #     #          `\r\n";
					}
					
					c.sendMessage(shaft + 
						  "`      ####     ####       `\r\n"
						+ "`     ##           ##      `\r\n"
						+ "`     #             #      `\r\n"
						+ "`     ##           ##      `\r\n"
						+ "`      ####     ####       `").queue();
					} else {
						c.sendMessage("you cant see a horse schlong as big as this").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
					}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} else {

			c.sendMessage(
					  "`         #######          `\r\n"
					+ "`        #   #   #         `\r\n"
					+ "`       #    #    #        `\r\n"
					+ "`       #         #        `\r\n"
					+ "`        #########         `\r\n"
					+ "`         #######          `\r\n"
					+ "`         #     #          `\r\n"
					+ "`         #     #          `\r\n"
					+ "`         #     #          `\r\n"
					+ "`         #     #          `\r\n"
					+ "`         #     #          `\r\n"
					+ "`         #     #          `\r\n"
					+ "`      ####     ####       `\r\n"
					+ "`     ##           ##      `\r\n"
					+ "`     #             #      `\r\n"
					+ "`     ##           ##      `\r\n"
					+ "`      ####     ####       `").queue();
		
		}
		
		
		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
	}
	

}
