package commands;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public class InsultCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel c, Message message) {
	    Emoji emoji = Emoji.fromUnicode("U+274C");
        message.addReaction(emoji).queue();

		List<Member> members = message.getMentions().getMembers();
		String[] args = message.getContentDisplay().split(" ");
		
		User author = message.getAuthor();
		
		System.out.println("InsultAuthor: "+ author.getName());

		if (args.length >= 2) {
			Random rand = new Random();
			int choice;

			for (Member mentionedMember : members) {

				choice = rand.nextInt(20);

				switch (choice) {

				case 1:

					c.sendMessage(mentionedMember.getEffectiveName() + ", du Bastard!").queue();
					break;
				case 2:
					c.sendMessage(mentionedMember.getEffectiveName() + ", du Huansohn!").queue();
					break;
				case 3:
					c.sendMessage(mentionedMember.getEffectiveName() + ", du Spast!").queue();
					break;
				case 4:
					c.sendMessage("Zurück in die Biotonne " + mentionedMember.getEffectiveName() + "!").queue();
					break;
				case 5:
					c.sendMessage("Schleich di " + mentionedMember.getEffectiveName()).queue();
					break;
				case 6:
					c.sendMessage(
							"Fact 142: " + mentionedMember.getEffectiveName() + " ist noch blöder als ein Stück Brot")
							.queue();
					break;
				case 7:
					c.sendMessage(
							"Selbst a Fiat500 schaut neben " + mentionedMember.getEffectiveName() + " attraktiv aus")
							.queue();
					break;
				case 8:
					c.sendMessage("Look at this noob --> " + mentionedMember.getEffectiveName()).queue();
					break;
				case 9:
					c.sendMessage("Mach die Kamera aus " + mentionedMember.getEffectiveName() + "! Hurensohn bist du")
							.queue();
					break;
				case 10:
					c.sendMessage("Wie wir alle wissen, ist " + mentionedMember.getEffectiveName() + " ein Affenarschfurchenfetischist").queue();
					break;
				case 11:
					c.sendMessage("Hier kommt "+mentionedMember.getEffectiveName() + " aka AnalAlfonso").queue();
					break;
				case 12:
					c.sendMessage(mentionedMember.getEffectiveName() + "'s Eltern waren wohl zu viel am Chromosomenfasching")
							.queue();
					break;
				case 13:
					c.sendMessage(mentionedMember.getEffectiveName() + ", du Diplomverkehrsinselbepflanzer")
							.queue();
					break;
				case 14:
					c.sendMessage(mentionedMember.getEffectiveName() + ", du LSD-Lukas")
							.queue();
					break;
				case 15:
					c.sendMessage(mentionedMember.getEffectiveName() + " oder auch DreckigerDan")
							.queue();
					break;
				case 16:
					c.sendMessage("Herzlichen Glückwunsch "+mentionedMember.getEffectiveName()+", Sie haben 7 neue Chromosomenpaare freigeschaltet und befinden sich jetzt im dreistelligen Bereich. Neue Unlocks: IchHabMichLieb-Jacke")
							.queue();
					break;
				case 17:
					c.sendMessage("Scheißeschmeisser Eisenbeisser " + mentionedMember.getEffectiveName())
							.queue();
					break;
				case 18:
					c.sendMessage(mentionedMember.getEffectiveName() + ", du Partytypenrunterholflittchen")
							.queue();
					break;
				case 19:
					c.sendMessage(mentionedMember.getEffectiveName() + ", du Fäkalienfressendesfastfoodfachpersonal")
							.queue();
					break;
					
					
				}
			}
		} else {
			c.sendMessage("illegal param.").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
		}

		message.delete().queueAfter(1500, TimeUnit.MILLISECONDS);
	}

}
