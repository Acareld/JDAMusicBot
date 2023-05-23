package manage;

import java.util.concurrent.ConcurrentHashMap;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

public class RoleManager {
	ConcurrentHashMap<Long, Role> joinRoles;
	
	
	public RoleManager() {
		this.joinRoles = new ConcurrentHashMap<Long, Role>();
	}
	
	
	public boolean setJoinRole(long roleId, Guild guild) {
		Role role;
		if((role = guild.getRoleById(roleId)) != null) {
			if(!this.joinRoles.containsKey(guild.getIdLong())) {
				this.joinRoles.put(guild.getIdLong(), role);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public Role getJoinRole(long guildid) {
		Role role = null;

		if (this.joinRoles.containsKey(guildid)) {
			role = this.joinRoles.get(guildid);
		} else {
			return null;
		}

		return role;
	}
}
