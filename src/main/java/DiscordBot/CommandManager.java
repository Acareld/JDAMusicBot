package DiscordBot;

import java.util.concurrent.ConcurrentHashMap;

import Music.commands.ClearQueueCommand;
import Music.commands.JoinCommand;
import Music.commands.PauseCommand;
import Music.commands.PlayCommand;
import Music.commands.PlayNextCommand;
import Music.commands.PlayNowCommand;
import Music.commands.QueueSizeCommand;
import Music.commands.RemoveLastCommand;
import Music.commands.RepeatCommand;
import Music.commands.RestartCommand;
import Music.commands.ResumeCommand;
import Music.commands.ShowQueueCommand;
import Music.commands.ShuffleCommand;
import Music.commands.SkipCommand;
import Music.commands.StopCommand;
import Music.commands.TrackInfoCommand;
import commands.BlowCommand;
import commands.ClearAllCommand;
import commands.ClearCommand;
import commands.CockCommand;
import commands.DynamicChannelCommand;
import commands.GasPriceCommand;
import commands.GetLogFileCommand;
import commands.HelpCommand;
import commands.InfoCommand;
import commands.InsultCommand;
import commands.MoveAllCommand;
import commands.ServerStatsCommand;
import commands.SetBotChannelCommand;
import commands.SetJoinRoleCommand;
import commands.SiegeRandomizerCommand;
import commands.VanishCommand;
import commands.WakeCommand;
import commands.picture.ConvolutionCommand;
import commands.picture.GrayScaleCommand;
import commands.picture.SepiaCommand;
import commands.picture.SubsamplingCommand;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class CommandManager {

	public ConcurrentHashMap<String, ServerCommand> commands;

	public CommandManager() {
		this.commands = new ConcurrentHashMap<>();

		// ---------------Admin
		// Commands--------------------------------------------------------------------//
	//	this.commands.put("giverole", new GiveRoleCommand());
		this.commands.put("info", new InfoCommand());
		this.commands.put("vanish", new VanishCommand());
		this.commands.put("setbotch", new SetBotChannelCommand());
		this.commands.put("setjoinrole", new SetJoinRoleCommand());
		
		this.commands.put("getlog", new GetLogFileCommand());
		this.commands.put("log", new GetLogFileCommand());

		// ---------------General
		// Commands--------------------------------------------------------------------//
		this.commands.put("clear", new ClearCommand());
		this.commands.put("insult", new InsultCommand());
		this.commands.put("wake", new WakeCommand());
		this.commands.put("clearbot", new ClearAllCommand());
		this.commands.put("serverstats", new ServerStatsCommand());
		this.commands.put("help", new HelpCommand());
		this.commands.put("moveall", new MoveAllCommand());
		this.commands.put("cock", new CockCommand());
		this.commands.put("blow", new BlowCommand());
		this.commands.put("r6", new SiegeRandomizerCommand());
		this.commands.put("sprit", new GasPriceCommand());
		this.commands.put("gas", new GasPriceCommand());
		
		this.commands.put("dynamic", new DynamicChannelCommand());

		// ---------------Music Commands-----------------------------------------------//
		this.commands.put("play", new PlayCommand());
		this.commands.put("p", new PlayCommand());

		this.commands.put("playnext", new PlayNextCommand());
		this.commands.put("pn", new PlayNextCommand());

		this.commands.put("playnow", new PlayNowCommand());

		this.commands.put("stop", new StopCommand());

		this.commands.put("skip", new SkipCommand());
		this.commands.put("s", new SkipCommand());

		this.commands.put("clearqueue", new ClearQueueCommand());
		this.commands.put("cq", new ClearQueueCommand());
		
		this.commands.put("queuesize", new QueueSizeCommand());
		this.commands.put("qs", new QueueSizeCommand());
		
		this.commands.put("showqueue", new ShowQueueCommand());
		this.commands.put("sq", new ShowQueueCommand());
		
		this.commands.put("join", new JoinCommand());
		this.commands.put("pause", new PauseCommand());
		this.commands.put("resume", new ResumeCommand());
		
		this.commands.put("trackinfo", new TrackInfoCommand());
		this.commands.put("tf", new TrackInfoCommand());

		this.commands.put("shuffle", new ShuffleCommand());
		
		this.commands.put("repeat", new RepeatCommand());
		this.commands.put("r", new RepeatCommand());
		
		this.commands.put("restart", new RestartCommand());
		
		this.commands.put("removelast", new RemoveLastCommand());
		this.commands.put("rl", new RemoveLastCommand());
		
	//	this.commands.put("rock", new RockPaperScissorsCommand());
		
		// ---------------Image Commands-----------------------------------------------//
		
		this.commands.put("sepia", new SepiaCommand());
        this.commands.put("grayscale", new GrayScaleCommand());
        this.commands.put("subsampling", new SubsamplingCommand());
        this.commands.put("convolution", new ConvolutionCommand());
	}

	public boolean perform(String command, Member m, TextChannel channel, Message message) {

		ServerCommand cmd;
		if ((cmd = this.commands.get(command.toLowerCase())) != null) {
			cmd.performCommand(m, channel, message);
			return true;
		}
		return false;
	}

}
