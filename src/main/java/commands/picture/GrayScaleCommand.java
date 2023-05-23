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
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.Message.Attachment;

public class GrayScaleCommand implements ServerCommand {

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
                    
                  //getting the rgb values and apply the grayscale filter
                    for(int i = 0; i < im.getWidth();i++) {
                        for(int j = 0; j < im.getHeight(); j++) {
                            Color col = new Color(im.getRGB(i,j));
                            
                            int grey = (col.getRed() + col.getGreen() + col.getBlue()) / 3;
                            Color newColor = new Color(grey, grey , grey);
                            
                            //construct new BufferedImage
                            bi.setRGB(i,j, newColor.getRGB());
                        }
                    }
                    
                    //sending new image with the grayscale filter applied
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ImageIO.write(bi, att.getFileExtension(), stream);
                //    c.sendFile(stream.toByteArray(), "greyscale: "+att.getFileName()).queue();
              
                    
                    
                } catch (Exception e) {
                    
                    e.printStackTrace();
                }
                
            } else {
                c.sendMessage("attachment is not an image").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
                message.delete().queue();;
            }
            
            
        } else {
            c.sendMessage("no attachment given").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
            message.delete().queue();
        }

    }

}
