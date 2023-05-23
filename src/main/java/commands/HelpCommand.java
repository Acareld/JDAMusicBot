package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class HelpCommand implements ServerCommand {
    
    
	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
		
		c.sendMessage("Which help section do you need?").setActionRow(sendButtons()).queue();
		
		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
	}
	
	private static List<Button> sendButtons() {
	    List<Button> buttons = new ArrayList<>();
	    
	    buttons.add(Button.primary("helpadmin", "admin"));
	    buttons.add(Button.success("helpgeneral", "general"));
	    buttons.add(Button.danger("helpmusic", "music"));
	    buttons.add(Button.secondary("helpimage", "image"));
	//    buttons.add(Button.secondary("helpcodec", "en-/decode"));
	    
	    return buttons;
	}
	
	

}
