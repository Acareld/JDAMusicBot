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

public class GasPriceCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message message) {
        Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();
        
        c.sendMessage("Which fuel price do you want to know?").setActionRow(sendButtons()).queue();
        
        message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);

    }
    
    
    private static List<Button> sendButtons() {
        List<Button> buttons = new ArrayList<>();
        
        buttons.add(Button.primary("fuelgas", "Gas / Super 95"));
        buttons.add(Button.secondary("fueldiesel", "Diesel"));
        
        return buttons;
    }
}
