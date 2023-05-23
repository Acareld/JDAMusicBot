package manage;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;

public class SiegeRandomizer {

	/**
	 * @param side
	 * @param member
	 * @return #MessageEmbed
	 */
	public static MessageEmbed onCall(int side, Member member) {
		
		ResultSet opSet = LiteSQL.onQuery("SELECT r6operator.op_id AS opid, r6operator.name AS opname FROM r6operator WHERE r6operator.side = "+side+" ORDER BY RANDOM() LIMIT 1");
		

		try {
			if(opSet.next()) {
				int opId = opSet.getInt("opid");
				String opName = opSet.getString("opname");
				
				ResultSet weaponSet = LiteSQL.onQuery("SELECT r6weapons.weapon_id AS weaponId, r6weapons.name AS wname FROM r6weapon_ops, r6weapons WHERE r6weapons.weapon_id = r6weapon_ops.weapon_id AND r6weapon_ops.op_id = "+opId+" ORDER BY RANDOM() LIMIT 1");
				
				if(weaponSet.next()) {
					
					String wName = weaponSet.getString("wname");
					ResultSet sidearmSet = LiteSQL.onQuery("SELECT r6sidearms.name AS sname FROM r6sidearm_ops, r6sidearms WHERE r6sidearm_ops.sidearm_id = r6sidearms.sidearm_id AND r6sidearm_ops.op_id = "+opId+" ORDER BY RANDOM() LIMIT 1");
					
					if(sidearmSet.next()) {
	
						String sName = sidearmSet.getString("sname");
						
						String sideString = "";
						Color color = Color.decode("#A80000");
						if(side == 0) {
							color = Color.decode("#B35809");
							sideString = "attacker";
						} else if(side == 1) {
							color = Color.decode("#075E8A");
							sideString = "defender";
						}
						
						EmbedBuilder builder = new EmbedBuilder();
						builder.setTitle("Randomized siege "+sideString);
						builder.setColor(color);
						
						Field result = new Field(opName,wName+"\n"+sName,false);
						
						
						builder.addField(result);
						builder.setFooter(member.getEffectiveName());
						
						
						return builder.build();
						
						
					} else {
						System.out.println("no sidearm found");
					}
				} else {
					System.out.println("no weapon found");
				}
			} else {
				System.out.println("no operator found");
			}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		EmbedBuilder errorBuilder = new EmbedBuilder();
		errorBuilder.setTitle("internal sql error");
		return errorBuilder.build();

	}
	
	public static MessageEmbed onCallGroup(int side, Member member, int count, Color color) {
		EmbedBuilder builder = new EmbedBuilder();
			
		try {
			
			for(int i = 0; i<count;i++) {
				ResultSet opSet = LiteSQL.onQuery("SELECT r6operator.op_id AS opid, r6operator.name AS opname FROM r6operator WHERE r6operator.side = "+side+" ORDER BY RANDOM() LIMIT 1");
				
				if(opSet.next()) {
					int opId = opSet.getInt("opid");
					String opName = opSet.getString("opname");
					
					ResultSet weaponSet = LiteSQL.onQuery("SELECT r6weapons.weapon_id AS weaponId, r6weapons.name AS wname FROM r6weapon_ops, r6weapons WHERE r6weapons.weapon_id = r6weapon_ops.weapon_id AND r6weapon_ops.op_id = "+opId+" ORDER BY RANDOM() LIMIT 1");
					
					if(weaponSet.next()) {
						
						String wName = weaponSet.getString("wname");
						ResultSet sidearmSet = LiteSQL.onQuery("SELECT r6sidearms.name AS sname FROM r6sidearm_ops, r6sidearms WHERE r6sidearm_ops.sidearm_id = r6sidearms.sidearm_id AND r6sidearm_ops.op_id = "+opId+" ORDER BY RANDOM() LIMIT 1");
						
						if(sidearmSet.next()) {
		
							String sName = sidearmSet.getString("sname");
							
							String sideString = "";
							
							if(side == 0) {
								sideString = "attacker";
							} else if(side == 1) {
								sideString = "defender";
							}
							
							
							builder.setTitle("Randomized siege "+sideString);
							builder.setColor(color);
							
							Field result = new Field(opName,wName+"\n"+sName,false);
							
							
							builder.addField(result);
		
						} else {
							System.out.println("no sidearm found");
						}
					} else {
						System.out.println("no weapon found");
					}
				} else {
					System.out.println("no operator found");
				}
		
			}
			builder.setFooter(member.getEffectiveName());
			return builder.build();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
