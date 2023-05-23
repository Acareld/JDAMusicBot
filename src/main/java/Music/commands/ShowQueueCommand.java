package Music.commands;

import java.util.List;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import DiscordBot.Bot;
import Music.MusicController;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ShowQueueCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();

		GuildVoiceState state;
		if ((state = m.getVoiceState()) != null) {
			AudioChannel vc;

			if ((vc = state.getChannel()) != null) {
				MusicController controller = Bot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
				List<AudioTrack> audioTrackList = controller.getQueue().getQueuelist();

				EmbedBuilder builder = new EmbedBuilder();
				builder.setColor(Color.decode("#1730eb"));

				if (audioTrackList.size() > 0) {
					builder.setTitle("Track in the queue");
					AudioTrackInfo info = audioTrackList.get(0).getInfo();
					Field field = new Field(info.title, "by " + info.author, false);
					builder.addField(field);

					if (audioTrackList.size() > 1) {
						builder.setTitle("Tracks in the queue");
						AudioTrackInfo info1 = audioTrackList.get(1).getInfo();
						Field field1 = new Field(info1.title, "by " + info1.author, false);
						builder.addField(field1);

						if (audioTrackList.size() > 2) {
							builder.setTitle("Tracks in the queue");
							AudioTrackInfo info2 = audioTrackList.get(2).getInfo();
							Field field2 = new Field(info2.title, "by " + info2.author, false);
							builder.addField(field2);

							if (audioTrackList.size() > 3) {
								builder.setTitle("Tracks in the queue");
								AudioTrackInfo info3 = audioTrackList.get(3).getInfo();
								Field field3 = new Field(info3.title, "by " + info3.author, false);
								builder.addField(field3);

								if (audioTrackList.size() > 4) {
									builder.setTitle("First 5 tracks in the queue");
									AudioTrackInfo info4 = audioTrackList.get(4).getInfo();
									Field field4 = new Field(info4.title, "by " + info4.author, false);
									builder.addField(field4);

									Field field5 = new Field("...",
											"+ " + String.valueOf(audioTrackList.size() - 6 + " more tracks in queue"),
											false);
									builder.addField(field5);
								}
							}
						}
					}
				} else {
					builder.setTitle("No tracks in the queue");
				}
				

				c.sendMessageEmbeds(builder.build()).queue();

			}
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
	}

}
