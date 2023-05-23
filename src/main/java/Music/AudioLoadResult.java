package Music;

import java.awt.Color;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import DiscordBot.Bot;
import Music.commands.PlayNextCommand;
import Music.commands.PlayNowCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class AudioLoadResult implements AudioLoadResultHandler {

	private final MusicController controller;
	private final String uri;

	public AudioLoadResult(MusicController controller, String uri) {
		this.controller = controller;
		this.uri = uri;
	}

	@Override
	public void trackLoaded(AudioTrack track) {
		Queue queue = controller.getQueue();
		AudioTrackInfo info = track.getInfo();
		TextChannel channel = Bot.INSTANCE.getChMan().getBotChannel(controller.getGuild().getIdLong());

		String trackInfo = info.title;

		EmbedBuilder builder = new EmbedBuilder();

		if (PlayNextCommand.getIsPlayNext()) {
			queue.addTrackToQueueNext(track);
			
			builder.setTitle("Added to queue and playing next");
			builder.setDescription(trackInfo);
			channel.sendMessageEmbeds(builder.build()).queue();
			PlayNextCommand.reset();
		} else if (PlayNowCommand.getIsPlayNow()) {
			queue.addTrackToQueueNext(track);
			
			builder.setTitle("Starts playing now");
			builder.setDescription(trackInfo);
			channel.sendMessageEmbeds(builder.build()).queue();
			
			queue.next();
			PlayNowCommand.reset();
		} else {
			queue.addTrackToQueue(track);
			
			builder.setTitle("Added to queue");
			builder.setDescription(trackInfo);
			channel.sendMessageEmbeds(builder.build()).queue();
		}

	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		Queue queue = controller.getQueue();
		TextChannel channel = Bot.INSTANCE.getChMan().getBotChannel(controller.getGuild().getIdLong());

		if (uri.startsWith("ytsearch: ")) {
			AudioTrackInfo info = playlist.getTracks().get(0).getInfo();

			String trackInfo = info.title;
			if (PlayNextCommand.getIsPlayNext()) {
				queue.addTrackToQueueNext(playlist.getTracks().get(0));
				EmbedBuilder builder = new EmbedBuilder();
				builder.setTitle("Added to queue and playing next");
				builder.setDescription(trackInfo);
				channel.sendMessageEmbeds(builder.build()).queue();

				PlayNextCommand.reset();
			} else if (PlayNowCommand.getIsPlayNow()) {
				queue.addTrackToQueueNext(playlist.getTracks().get(0));
				EmbedBuilder builder = new EmbedBuilder();
				builder.setTitle("Starts playing now");
				builder.setDescription(trackInfo);
				channel.sendMessageEmbeds(builder.build()).queue();
				
				queue.next();
				PlayNowCommand.reset();
			} else {
				queue.addTrackToQueue(playlist.getTracks().get(0));
				EmbedBuilder builder = new EmbedBuilder();
				builder.setTitle("Added to queue");
				builder.setDescription(trackInfo);
				channel.sendMessageEmbeds(builder.build()).queue();
			}
			return;
		}

		int added = 0;

		if (PlayNextCommand.getIsPlayNext() || PlayNowCommand.getIsPlayNow()) {
			for (AudioTrack track : playlist.getTracks()) {
				queue.addTrackToQueueNext(track);
				added++;
			}
		} else if (PlayNowCommand.getIsPlayNow()) {
			for (AudioTrack track : playlist.getTracks()) {
				queue.addTrackToQueueNext(track);
				added++;
			}
			queue.next();
		} else {
			for (AudioTrack track : playlist.getTracks()) {
				queue.addTrackToQueue(track);
				added++;
			}
		}

		EmbedBuilder builder = new EmbedBuilder().setColor(Color.decode("#8c14fc"))
				.setDescription(added + " tracks added to the queue");
		channel.sendMessageEmbeds(builder.build()).queue();

	}

	@Override
	public void noMatches() {
		System.out.println("noMatches");
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		System.out.println("loadFailed");
	}

}
