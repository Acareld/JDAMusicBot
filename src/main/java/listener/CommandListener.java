package listener;

import java.util.concurrent.TimeUnit;

import DiscordBot.Bot;
import manage.ChannelManager;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
	public String prefix = "!";

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {				
	    Emoji emoji = Emoji.fromUnicode("U+274C");
	    ChannelManager chMan = Bot.INSTANCE.getChMan();
		String message = event.getMessage().getContentDisplay();

		if (event.isFromType(ChannelType.TEXT)) {
			TextChannel channel = event.getChannel().asTextChannel();

			if (message.startsWith(prefix)) { 
				String[] args = message.substring(1).split(" ");
				TextChannel botChannel;
				
				if((botChannel = chMan.getBotChannel(event.getGuild().getIdLong())) != null || args[0].equalsIgnoreCase("setbotch") || args[0].equalsIgnoreCase("help")) {				

					if (chMan.checkBotCh(event.getChannel().asTextChannel(), event.getGuild()) || args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("setbotch") || args[0].equalsIgnoreCase("help")) {
	
						if (args.length > 0) {
							if (!Bot.INSTANCE.getCmdMan().perform(args[0], event.getMember(), channel,
									event.getMessage())) {
							    
								event.getMessage().addReaction(emoji).queue();
								channel.sendMessage("Unknown Command").complete().delete().queueAfter(1500,
										TimeUnit.MILLISECONDS);
								event.getMessage().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
							}
						}
					} else {
						event.getMessage().addReaction(emoji).queue();
						channel.sendMessage("wrong channel, bot commands only work in the set bot channel").complete().delete()
								.queueAfter(1500, TimeUnit.MILLISECONDS);
						event.getMessage().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
					}
				} else {
					channel.sendMessage("no bot channel set").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
					event.getMessage().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
				}
			}

		}
	}
}
