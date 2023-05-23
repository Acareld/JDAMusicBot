package manage;

public class SQLManager {
	public static void onCreate() {

		LiteSQL.onUpdate(
				"CREATE TABLE IF NOT EXISTS reactroles(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid INTEGER, channelid INTEGER, messageid INTEGER, emote VARCHAR, rollenid INTEGER)");

		LiteSQL.onUpdate(
				"CREATE TABLE IF NOT EXISTS timeranks(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid INTEGER, guildid INTEGER, time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

		LiteSQL.onUpdate(
				"CREATE TABLE IF NOT EXISTS statchannels(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid INTEGER, categoryid INTEGER)");

		LiteSQL.onUpdate(
				"CREATE TABLE IF NOT EXISTS musicchannel(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, guildid INTEGER, channelid INTEGER)");
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS r6operator(op_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name STRING, side INTEGER, count INTEGER)");
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS r6weapon_ops(op_id INTEGER, weapon_id INTEGER, count INTEGER)");
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS r6weapons(weapon_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name STRING)");
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS r6sidearms(sidearm_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name STRING)");
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS r6sidearm_ops(op_id INTEGER, sidearm_id INTEGER)");

	}
	
}
