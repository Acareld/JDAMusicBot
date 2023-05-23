package commands;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import DiscordBot.Bot;
import commands.types.ServerCommand;
import manage.ChannelManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class SetBotChannelCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
		
		EmbedBuilder builder = new EmbedBuilder();
		ChannelManager chMan = Bot.INSTANCE.getChMan();
		String[] args = message.getContentRaw().split(" ");
		
		if(args.length >= 2) {
			try {
				long id = Long.parseLong(args[1]);
				
				if(chMan.setBotChannel(id, c.getGuild())) {
					builder.setTitle("successfully set "+chMan.getBotChannel(c.getGuild().getIdLong()).getName()+" as the bot command channel");
					builder.setColor(Color.decode("#119c21"));
					chMan.getBotChannel(c.getGuild().getIdLong()).sendMessageEmbeds(builder.build()).queue();
				} else {
					builder.setTitle("could not find stated textchannel or there is already a botchannel set");
					builder.setColor(Color.decode("#ED1A1A"));
					c.sendMessageEmbeds(builder.build()).queue();
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
				
		}
		
		
		
		
		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
	}
	

}
