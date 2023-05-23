package Music.commands;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import DiscordBot.Bot;
import Music.MusicController;
import Music.TrackScheduler;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class RepeatCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message message) {
        Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();

        GuildVoiceState state;
        if ((state = m.getVoiceState()) != null) {
            AudioChannel vc;
            if ((vc = state.getChannel()) != null) {
                MusicController controller = Bot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
                TrackScheduler scheduler = controller.getScheduler();
                AudioManager manager = vc.getGuild().getAudioManager();
                AudioChannel botCh = manager.getConnectedChannel();

                if (vc.equals(botCh)) {
                    if (scheduler.getRepeat()) {
                        scheduler.setRepeat(false);

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("No longer on repeat");
                        builder.setColor(Color.decode("#1730eb"));
                        c.sendMessageEmbeds(builder.build()).queue();
                    } else {
                        scheduler.setRepeat(true);

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("Now on repeat");
                        builder.setColor(Color.decode("#1730eb"));
                        c.sendMessageEmbeds(builder.build()).queue();
                    }
                } else {
                    c.sendMessage("you must be connected to the same vc as the bot").complete().delete()
                            .queueAfter(1500, TimeUnit.MILLISECONDS);
                }

            } else {
                c.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500,
                        TimeUnit.MILLISECONDS);
            }
        } else {
            c.sendMessage("you are not connected to a vc").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
        }

        message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
    }
}
