package commands;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.Color;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;


public class MoveAllCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();

		String[] args = message.getContentDisplay().split("\"");
		Guild guild = c.getGuild();

		if (m.hasPermission(Permission.KICK_MEMBERS)) {
		    GuildVoiceState state1;
		    
		    if((state1 = m.getVoiceState()) != null) {
		       List<VoiceChannel> vc = guild.getVoiceChannels(); 
		       
		    }

			if (args.length == 2) {

				List<VoiceChannel> channels = guild.getVoiceChannelsByName(args[1], false);

				if (channels.size() >= 1) {
					AudioChannel targetAc = channels.get(0);
					GuildVoiceState state;

					if ((state = m.getVoiceState()) != null) {
						AudioChannel ac;

						if ((ac = state.getChannel()) != null) {
							List<Member> members = ac.getMembers();
							int count = 0;

							for (Member conMember : members) {
								guild.moveVoiceMember(conMember, targetAc).queue();
								count++;
							}
							EmbedBuilder builder = new EmbedBuilder();
							builder.setTitle("Moved " + count + " members to voicechannel " + args[1]);
							builder.setColor(Color.decode("#ED1A1A"));
							c.sendMessageEmbeds(builder.build()).queue();
						} else {
							c.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500,
									TimeUnit.MILLISECONDS);
						}
					} else {
						c.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500,
								TimeUnit.MILLISECONDS);
					}
				} else {
					c.sendMessage("could not find voicechannel").complete().delete().queueAfter(1500,
							TimeUnit.MILLISECONDS);
				}
			} else {
				c.sendMessage("No voicechannel stated or not written between \"").complete().delete().queueAfter(1500,
						TimeUnit.MILLISECONDS);
			}
		} else {
			c.sendMessage("you do not have the rights to do that").complete().delete().queueAfter(1500,
					TimeUnit.MILLISECONDS);
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
	}

}
