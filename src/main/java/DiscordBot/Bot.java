package DiscordBot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import Music.PlayerManager;
import listener.ButtonClick;
import listener.CommandListener;
import listener.JoinListener;
import manage.ChannelManager;
import manage.LiteSQL;
import manage.RoleManager;
import manage.SQLManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot {
	public static Bot INSTANCE;
	
	private static String onlineLogPath = "/home/pi/DCBot/botOnlineLog.txt";

	public AudioPlayerManager audioPlayerManager;
	public ShardManager shardMan;
	private Thread loop;
	public PlayerManager playerManager;
	private CommandManager cmdMan;
	public ChannelManager chMan;
	public RoleManager rMan;
	

	public static void main(String[] args) throws LoginException {

		try {
			new Bot();
		} catch (LoginException | IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	public Bot() throws LoginException, IllegalArgumentException {
		INSTANCE = this;
		
		LiteSQL.connect();
		SQLManager.onCreate();
		
		//Log File
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
           
        try {
            File file = new File(onlineLogPath);           
            if(file.createNewFile()) {
                FileWriter writer = new FileWriter(onlineLogPath);
                writer.write(dtf.format(time) + " Bot online!\n");
                writer.close();
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(onlineLogPath, true));
                writer.append(dtf.format(time) + " Bot online!\n");
                writer.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder
				.createDefault("");  

		builder.setActivity(Activity.competing("!help"));
		builder.setStatus(OnlineStatus.ONLINE);
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES);
		builder.setChunkingFilter(ChunkingFilter.ALL);
		builder.setMemberCachePolicy(MemberCachePolicy.ALL);
		builder.enableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT);
		

		this.audioPlayerManager = new DefaultAudioPlayerManager();
		this.playerManager = new PlayerManager();
		this.cmdMan = new CommandManager();
		this.chMan = new ChannelManager();
		this.rMan = new RoleManager();
		
		builder.addEventListeners(new CommandListener());
		builder.addEventListeners(new JoinListener());
		builder.addEventListeners(new ButtonClick());

		shardMan = builder.build();
		
		

		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);
		shutdown();
		runLoop();
	}

	public void shutdown() {
		new Thread(() -> {

			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				while ((line = reader.readLine()) != null) {

					if (line.equalsIgnoreCase("exit")) {
						shutdown = true;
						if (shardMan != null) {
							shardMan.setStatus(OnlineStatus.OFFLINE);
							shardMan.shutdown();
							LiteSQL.disconnect();
							System.out.println("Bot offline.");
						}

						reader.close();
						break;
					} else if (line.equalsIgnoreCase("info")) {
						for (Guild guild : shardMan.getGuilds()) {
							System.out.println(guild.getName() + " " + guild.getIdLong());
						}
					} else {
						System.out.println("Use 'exit' to shutdown.");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}).start();
	}

	public boolean shutdown = false;

	public void runLoop() {
		this.loop = new Thread(() -> {

			long time = System.currentTimeMillis();

			while (!shutdown) {
				if (System.currentTimeMillis() >= time + 100000) {
					time = System.currentTimeMillis();
					onMinutes();
				}
			}
		});
		this.loop.setName("Loop");
		this.loop.start();
	}

	String[] status = new String[] { "cotton picking", "milf hunting", "fortnut esl", "beating up kids" };
	int[] colors = new int[] { 0x42b6f5, 0x42a1f5 };
	int next = 30;

	public void onMinutes() {
	    
	}

	public CommandManager getCmdMan() {
		return cmdMan;
	}

	public ShardManager getShardMan() {
		return shardMan;
	}
	public ChannelManager getChMan() {
		return chMan;
	}
	public RoleManager getRMan() {
		return rMan;
	}
	public static String getOnlineLogPath() {
        return onlineLogPath;
    }
	

}
