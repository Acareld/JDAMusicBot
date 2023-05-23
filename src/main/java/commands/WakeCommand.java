package commands;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.TimeUnit;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class WakeCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();

		List<Member> members = message.getMentions().getMembers();
		String[] args = message.getContentDisplay().split(" ");
		Guild guild = c.getGuild();
		GuildVoiceState state;
		VoiceChannel vcAfk = guild.getAfkChannel();
		if (args.length >= 2) {
			for (Member mentionedMember : members) {
				if ((state = mentionedMember.getVoiceState()) != null) {
					AudioChannel vc = state.getChannel();
					if (state.isSelfDeafened()) {
						if ((vc = state.getChannel()) != null) {
							for (int i = 0; i < 3; i++) {
								guild.moveVoiceMember(mentionedMember, vcAfk).queue();
								guild.moveVoiceMember(mentionedMember, vc).queue();

							}
							EmbedBuilder builder = new EmbedBuilder();
							builder.setDescription("Hey " + mentionedMember.getAsMention() + ", wake up!");
							builder.setColor(Color.decode("#0E0BE4"));
							c.sendMessageEmbeds(builder.build()).queue();

						} else {
							c.sendMessage("mentioned member is not connected to a vc").complete().delete()
									.queueAfter(1500, TimeUnit.MILLISECONDS);
						}

					} else {
						c.sendMessage("you can only wake self-deafened members").complete().delete().queueAfter(1500,
								TimeUnit.MILLISECONDS);
					}
				} else {
					c.sendMessage("mentioned member is not connected to a vc").complete().delete().queueAfter(1500,
							TimeUnit.MILLISECONDS);
				}

			}

		} else {
			c.sendMessage("please mention a member to wake up").complete().delete().queueAfter(1500,
					TimeUnit.MILLISECONDS);
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
	}

}
