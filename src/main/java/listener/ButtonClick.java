package listener;

import java.awt.Color;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import manage.SiegeRandomizer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonClick extends ListenerAdapter {
    private static String music = "`//--------Music Commands------------------------------------------------------------------------------//`\r\n"
            + "\"play|p [yt-link/yt-search-query]\" -------->    extracts the audio of the stated link/search result\r\n"
            + "                                and starts playing. If the player is already playing\r\n"
            + "                                the audio result will be added to the queue\r\n"
            + "\r\n"
            + "\"playnext|pn [yt-link/yt-search-query]\" --->    same procedure as \"play|p\", but if there is a track \r\n"
            + "                                already playing the audio result will be added      \r\n"
            + "                                to the queue at index 0\r\n"
            + "\r\n"
            + "\"playnow [yt-link/yt-search-query]\" ------->    same as \"playnext|pn\" but skips the currently playing track\r\n"
            + "\r\n"
            + "\"stop\" ------------------------------------>    stops the player, clears the queue and disconnects the Bot\r\n"
            + "\r\n"
            + "\"skip|s\" ---------------------------------->    skips the currently playing track\r\n"
            + "\r\n"
            + "\"clearqueue|cq\" --------------------------->    clears the queue \r\n"
            + "\r\n"
            + "\"removelast|rl\" --------------------------->    removes the last added track from the queue\r\n"
            + "\r\n"
            + "\"queuesize|qs\" ---------------------------->    returns the number of tracks currently in the queue\r\n"
            + "\r\n"
            + "\"showqueue|sq\" ---------------------------->    displays the next 5 tracks in queue with title and author\r\n"
            + "\r\n"
            + "\"join\" ------------------------------------>    connects the Bot to your current voicechannel\r\n"
            + "\r\n"
            + "\"pause\" ----------------------------------->    pauses the player\r\n"
            + "\r\n"
            + "\"resume\" ---------------------------------->    resumes the player\r\n"
            + "\r\n"
            + "\"trackinfo|tf\" ---------------------------->    lists information about the currently playing track\r\n"
            + "\r\n"
            + "\"shuffle\" --------------------------------->    shuffles the current queue\r\n"
            + "\r\n"
            + "\"repeat|r\" -------------------------------->    repeats the currently playing song, call again to cancel\r\n"
            + "\r\n"
            + "\"restart\" --------------------------------->    restarts the currently playing song";
    
  
    
    private static String general = "`//--------General Commands----------------------------------------------------------------------------//`\r\n"
            + "\"clear [int]\" ---------------------------->     clears number of messages stated in [int]\r\n"
            + "                                \r\n"
            + "\"wake [member member...]\" ----------------->    moves the mentioned member 3 times to the set afk \r\n"
            + "                                channel and back, only works if member is selfdeafened\r\n"
            + "\r\n"
            + "\"moveall \"[voicechannel]\" \" --------------->    moves all the users connected to the authors voicechannel\r\n"
            + "                                to the stated voicechannel between \"\"\r\n"
            + "\r\n"
            + "\"help\" ------------------------------------>    DMs the author with a list of possible commands\r\n"
            + "\r\n"
            + "\"r6 [def/att] [int]\" ---------------------->    returns a random operator and weapon for the stated\r\n"
            + "                                side, [int] changes the amount the bot will return\r\n"
            + "\r\n"
            + "\"r6 round\"--------------------------------->    returns 3 random attackers and 3 random defenders \r\n"
            + "\r\n"
            + "\"blow [member|string]\"--------------------->    sends an ascii art of you getting a bj from the stated \r\n"
            + "                                member|string\r\n"
            + "\r\n"
            + "\"cock [int]\"------------------------------> sends an ascii art of a penis with the stated length in\r\n"
            + "                                int or default length 6\r\n"
            + "\r\n"
            + "\"insult [member member...]\" --------------->    insults the mentioned members\r\n"
            + "\r\n"
            + "\"gas|sprit\" ------------------------------->    gives you the choice between gas and diesel to show the \r\n"
            + "                                current price\r\n"
            + "\r\n"
            + "\"serverstats\"------------------------------>    CURRENTLY WORKING ON shows some stats for the current guild";
    
    private static String admin = "`//--------Admin Commands------------------------------------------------------------------------------//`\r\n"
            + "\"setbotch [textchannelId]\"----------------->    MUST BE USED AFTER INVITING THE BOT\r\n"
            + "                                sets the botchannel for the current guild if found with \r\n"
            + "                                the stated textchannelId \r\n"
            + "                                (right click on texchannel -> copy id)\r\n"
            + "\r\n"
            + "\"setjoinrole [roleId]\"--------------------->    the stated role will be, when found, assigned to all new \r\n"
            + "                                members joining the guild\r\n"
            + "\r\n"
            + "\"giverole [roleId] [member member...]\"----->    gives the mentioned member the specific role when found\r\n"
            + "                                with the given roleId, only possible when having\r\n"
            + "                                MANAGE_ROLE permission\r\n"
            + "                                (role settings -> 3 dots -> copy id)\r\n"
            + "\r\n"
            + "\"getlog|log\"------------------------------->    Sends you the log file when the bot went online via a \r\n"
            + "                                private message";
    
    private static String image = "`//--------Image Commands------------------------------------------------------------------------------//`\r\n"
            + "IMAGE MUST BE ATTACHED TO COMMAND MESSAGE\r\n"
            + "supported formats: \"jpg\", \"jpeg\", \"png\", \"gif\", \"webp\", \"tiff\", \"svg\", \"apng\"\r\n"
            + "\r\n"
            + "\"sepia\"------------------------------------>    applies a sepia filter to the picture\r\n"
            + "\r\n"
            + "\"grayscale\"-------------------------------->    applies a grayscale filter to the picture\r\n"
            + "\r\n"
            + "\"convolution [\"sharpen\"|\"edge\"]\"----------->    applies a convolution filter to the picture for\r\n"
            + "                                either sharpening or edge detection\r\n"
            + "\r\n"
            + "\"subsampling [sampling rate]\"-------------->    applies subsampling with the given rate [1-8] to the picture";
    
    private static String code;
    private static String url = "https://www.bmk.gv.at/themen/energie/preise/aktuelle_preise.html";
    
    
    
    
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        
        Member m = event.getMember();
        User author = m.getUser();
        MessageChannel c = event.getChannel();
        
        
        int rockPaperScissorsCount = 0;
        String lastMove = "";
        
        
        
        switch(event.getButton().getId()) {
        case "helpadmin":
            event.reply("Sent admin help to you private").setEphemeral(true).queue();
           // author.openPrivateChannel().flatMap(ch -> ch.sendMessage(admin)).queue();
            author.openPrivateChannel().queue(pc -> pc.sendMessage(admin).queue());
            break;
        case "helpgeneral":
            event.reply("Sent admin help to you private").setEphemeral(true).queue();
            author.openPrivateChannel().flatMap(ch -> ch.sendMessage(general)).queue();
            break;
        case "helpcodec":
            author.openPrivateChannel().flatMap(ch -> ch.sendMessage(code)).queue();
            break;
        case "helpmusic":
            event.reply("Sent music help to you private").setEphemeral(true).queue();
            author.openPrivateChannel().flatMap(ch -> ch.sendMessage(music)).queue();
            break;
        case "helpimage":
            event.reply("Sent image help to you private").setEphemeral(true).queue();
            author.openPrivateChannel().flatMap(ch -> ch.sendMessage(image)).queue();
            break;
        case "r6att":
            event.deferEdit();
            c.sendMessageEmbeds(SiegeRandomizer.onCall(0,m)).queue();
            break;
        case "r6def":
            event.deferEdit();
            c.sendMessageEmbeds(SiegeRandomizer.onCall(1,m)).queue();
            break;
        case "r6round":
            event.deferEdit();
            Random rand = new Random();
            
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            
            Color color = new Color(r,g,b);
            c.sendMessageEmbeds(SiegeRandomizer.onCallGroup(0, m, 3, color)).queue();
            c.sendMessageEmbeds(SiegeRandomizer.onCallGroup(1, m, 3, color)).queue();
            break;
        case "rock":
            event.reply("Chose \"Rock\" as your move").setEphemeral(true).queue();
            if(rockPaperScissorsCount < 1) {
                lastMove = "rock";
                rockPaperScissorsCount++;
            } else {
                if(lastMove.equalsIgnoreCase("rock")) {
                    c.sendMessage("TIE").queue();
                } else if(lastMove.equalsIgnoreCase("paper")) {
                    c.sendMessage("Paper wins!").queue();
                } else {
                    c.sendMessage("Rock wins!").queue();
                }
                lastMove = "";
                rockPaperScissorsCount = 0;
            }
            break;
        case "paper":
            event.reply("Chose \"Paper\" as your move").setEphemeral(true).queue();
            if(rockPaperScissorsCount < 1) {
                lastMove = "paper";
                rockPaperScissorsCount++;
            } else {
                if(lastMove.equalsIgnoreCase("paper")) {
                    c.sendMessage("TIE").queue();
                } else if(lastMove.equalsIgnoreCase("rock")) {
                    c.sendMessage("Paper wins!").queue();
                } else {
                    c.sendMessage("Scissors wins!").queue();
                }
                lastMove = "";
                rockPaperScissorsCount = 0;
            }
            break;
        case "scissors":
            event.reply("Chose \"Scissors\" as your move").setEphemeral(true).queue();
            if(rockPaperScissorsCount < 1) {
                lastMove = "scissors";
                rockPaperScissorsCount++;
            } else {
                if(lastMove.equalsIgnoreCase("scissors")) {
                    c.sendMessage("TIE").queue();
                } else if(lastMove.equalsIgnoreCase("paper")) {
                    c.sendMessage("Scissors wins!").queue();
                } else {
                    c.sendMessage("rock wins!").queue();
                }
                lastMove = "";
                rockPaperScissorsCount = 0;
            }
            break;
        case "resetRock":
            event.deferEdit();
            rockPaperScissorsCount = 0;
            lastMove = "";
            break;
        case "fuelgas":
            event.deferEdit();
         // GasPriceCommand
            
            String gas = "";
            try {
                final Document document = Jsoup.connect(url).get();
                
                Elements table = document.select("table.table");    
                Element row = table.select("tr").get(1);
                
                gas = row.select("td:nth-of-type(3)").text();
            } catch (Exception e) {
                e.printStackTrace();
            }
            event.getMessage().delete().queue();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Current gas price (AUT)");
            builder.setDescription(gas + "\u20ac");
            c.sendMessageEmbeds(builder.build()).queue();
            break;
        case "fueldiesel":
            event.deferEdit();
         // GasPriceCommand
            String diesel = "";
            
            try {
                final Document document = Jsoup.connect(url).get();
                
                Elements table = document.select("table.table");    
                Element row = table.select("tr").get(1);
                diesel = row.select("td:nth-of-type(2)").text();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            event.getMessage().delete().queue();
            EmbedBuilder builder1 = new EmbedBuilder();
            builder1.setTitle("Current diesel price (AUT)");
            builder1.setDescription(diesel + "\u20ac");
            c.sendMessageEmbeds(builder1.build()).queue();
            break;
        default:
            break;
        }
    }
}
