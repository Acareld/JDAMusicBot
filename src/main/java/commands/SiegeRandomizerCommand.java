package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

import commands.types.ServerCommand;
import manage.SiegeRandomizer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class SiegeRandomizerCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
		
		Random rand = new Random();
		
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		
		Color color = new Color(r,g,b);
		
		String[] args = message.getContentDisplay().split(" ");
		
		if(args.length == 1) {
		     c.sendMessage("Please choose side for one operator or group for the whole round").setActionRow(sendButtons()).queue();

		} else if(args.length == 3) {
			try {
				int amount = Integer.parseInt(args[2]);
				
				if(args[1].equalsIgnoreCase("att")) {
					c.sendMessageEmbeds(SiegeRandomizer.onCallGroup(0, m, amount, color)).queue();
				} else if(args[1].equalsIgnoreCase("def")) {
					c.sendMessageEmbeds(SiegeRandomizer.onCallGroup(1, m, amount, color)).queue();
				} else {
					 c.sendMessage("please state either 'def' or 'att'").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
				 }
								

			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} else {
			c.sendMessage("illegal parameters").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
		}
		
		
		
		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
	}
	
	
	private static List<Button> sendButtons() {
        List<Button> buttons = new ArrayList<>();
        
        buttons.add(Button.primary("r6def", "defender")); 
        buttons.add(Button.danger("r6att", "attacker"));
        buttons.add(Button.success("r6round", "round"));
               
        return buttons;
    }

}
