package commands.picture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class SepiaCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message message) {
        //retrieving attachments
        List<Attachment> list = message.getAttachments();
        
        if(list.size() == 1) {
            Attachment att = list.get(0);
            
            if(att.isImage()) {
                try {
                    //retrieving a BufferedImage from the InputStream of the attachment
                    InputStream in = att.retrieveInputStream().get();
                    BufferedImage im = ImageIO.read(in);
                    in.close();
                    //new BufferedImage
                    BufferedImage bi = new BufferedImage(im.getWidth(), im.getHeight(), im.getType());
                    
                    //getting the rgb values and apply the sepia filter
                    for(int i = 0; i < im.getWidth(); i++) {
                        for(int j = 0; j < im.getHeight(); j++) {
                            Color col = new Color(im.getRGB(i,j));
                            
                            int sepiaRed = (int) ((col.getRed() * 0.393) + (col.getGreen() * 0.769) + (col.getBlue() * 0.189));
                            int sepiaGreen = (int) ((col.getRed() * 0.349) + (col.getGreen() * 0.686) + (col.getBlue() * 0.168));
                            int sepiaBlue = (int) ((col.getRed() * 0.272) + (col.getGreen() * 0.534) + (col.getBlue() * 0.131));
                            if(sepiaRed > 255) {
                                sepiaRed = 255;
                            }
                            if(sepiaGreen > 255) {
                                sepiaGreen = 255;
                            }
                            if(sepiaBlue > 255) {
                                sepiaBlue = 255;
                            }
                            
                            Color newColor = new Color(sepiaRed, sepiaGreen, sepiaBlue);
                            
                            //construct new BufferedImage
                            bi.setRGB(i, j, newColor.getRGB());
                            
                        }
                    }
                    
                    //sending new image with the sepia filter applied
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ImageIO.write(bi, att.getFileExtension(), stream);
                 //   c.sendFile(stream.toByteArray(), "sepia: "+att.getFileName()).queue();
              
                    
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            } else {
                c.sendMessage("attachment is not an image").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
                message.delete().queue();
            }
            
            
        } else {
            c.sendMessage("no attachment given").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
            message.delete().queue();
        }

    }

}
