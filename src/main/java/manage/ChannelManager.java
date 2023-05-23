package manage;

import java.util.concurrent.ConcurrentHashMap;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ChannelManager {
	ConcurrentHashMap<Long, TextChannel> botChannel;
	public volatile int dynamicVc;
	
	public ChannelManager() {
		this.botChannel = new ConcurrentHashMap<Long, TextChannel>();
		dynamicVc = 0;
	}
	 
	
	public boolean setBotChannel(long id, Guild guild) {
		TextChannel tc;
		if((tc = guild.getTextChannelById(id)) != null) {
			if(!this.botChannel.containsKey(guild.getIdLong())) {
				this.botChannel.put(guild.getIdLong(), tc);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public TextChannel getBotChannel(long guildid) {
		TextChannel tc = null;

		if (this.botChannel.containsKey(guildid)) {
			tc = this.botChannel.get(guildid);
		} 
		return tc;
	}
	
	public boolean checkBotCh(TextChannel tc, Guild guild) {
		if(this.botChannel.containsKey(guild.getIdLong())) {
		    
			if(tc.getIdLong() == botChannel.get(guild.getIdLong()).getIdLong()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	
	
	
}
