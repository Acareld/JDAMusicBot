package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

public class ClearCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
		
		if (m.hasPermission(c, Permission.MESSAGE_MANAGE)) {
			String[] args = message.getContentDisplay().split(" ");

			if (args.length == 2) {
				try {
					int amount = Integer.parseInt(args[1]);
					c.purgeMessages(get(c, amount));
					c.sendMessage("Deleted " + amount + " messages").complete().delete().queueAfter(1500,
							TimeUnit.MILLISECONDS);
					return;

				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (ErrorResponseException e) {
					System.out.println("Message not found");
				} 
			} else {
				c.sendMessage("illegal param.").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
			}
		} else {
			c.sendMessage("you do not have the permission to do that").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
		}
		
		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);

	}

	public List<Message> get(MessageChannel channel, int amount) {
		List<Message> messages = new ArrayList<>();
		int i = amount + 1;
		for (Message message : channel.getIterableHistory().cache(false)) {
			if (!message.isPinned()) {
				messages.add(message);

			}
			if (--i <= 0) {
				break;
			}
		}

		return messages;
	}

}
