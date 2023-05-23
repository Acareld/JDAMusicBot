package commands;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import DiscordBot.Bot;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class SetJoinRoleCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
		
		EmbedBuilder builder = new EmbedBuilder();
		
		String[] args = message.getContentRaw().split(" ");
		
		if(args.length >= 2) {
			try {
				long id = Long.parseLong(args[1]);
				
				if(Bot.INSTANCE.getRMan().setJoinRole(id, c.getGuild())) {
					builder.setTitle("successfully set "+Bot.INSTANCE.getRMan().getJoinRole(c.getGuild().getIdLong()).getName()+" as the join role");
					builder.setColor(Color.decode("#119c21"));
					c.sendMessageEmbeds(builder.build()).queue();
				} else {
					builder.setTitle("could not find stated role");
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
