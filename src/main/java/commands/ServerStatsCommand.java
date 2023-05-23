package commands;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ServerStatsCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();

		String[] args = message.getContentDisplay().split(" ");
		Guild guild = m.getGuild();
	//	Task<List<Member>> membersTask = guild.loadMembers();
	//	List<Member> members = membersTask.get();

		SimpleDateFormat time = new SimpleDateFormat("HH:mm");
		SimpleDateFormat date = new SimpleDateFormat("dd.MM.YYYY");

		if (args.length >= 2) {
			c.sendMessage("no args needed").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
		} else {
			EmbedBuilder builder = new EmbedBuilder();
			builder.setTitle("Current server stats for " + guild.getName());
			builder.setDescription("Owner: " + guild.getOwner().getEffectiveName());
			
			Field field = new Field("Total members", String.valueOf(guild.getMemberCount()), false);		
			builder.addField(field);
			
		/*	int online = 0;
			for (Member member : members) {

				if (member.getOnlineStatus() != OnlineStatus.OFFLINE && !member.getUser().isBot()) {
					online++;
				}
			}	
			Field field1 = new Field("Total online members", String.valueOf(online), false);
			builder.addField(field1); */

			Field field2 = new Field("Time & Date", "Time: " + time.format(Calendar.getInstance().getTime()) + "\n Date: "
					+ date.format(Calendar.getInstance().getTime()), false, false);
			builder.addField(field2);

			builder.setColor(Color.decode("#21C704"));
			c.sendMessageEmbeds(builder.build()).queue();
			message.delete().queueAfter(3, TimeUnit.SECONDS);
		}

	}

}
