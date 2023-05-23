package commands.picture;

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

public class SubsamplingCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message message) {
        //splitting the command message
        String[] args = message.getContentDisplay().substring(1).split(" ");
        
        //retrieving attachments
        List<Attachment> list = message.getAttachments();
        
        if(list.size() == 1) {
            Attachment att = list.get(0);
            
            if(att.isImage()) {
                if(args.length >= 2) {
                    try {
                        //retrieve subsampling rate
                        int rate = Integer.parseInt(args[1]);
                        
                        
                        if(rate > 0 && rate <= 8) {
                          //retrieving a BufferedImage from the InputStream of the attachment
                            InputStream in = att.retrieveInputStream().get();
                            BufferedImage im = ImageIO.read(in);
                            in.close();
                            //new BufferedImage
                            BufferedImage bi = new BufferedImage(im.getWidth()/rate+1, im.getHeight()/rate+1, im.getType());
                            
                            int newX = 0;
                            int newY = 0;
                            //getting the pixel values and apply the subsampling
                            for(int i = 0; i < im.getWidth(); i += rate) {
                                for(int j = 0; j < im.getHeight(); j += rate) {
                                    //construct new BufferedImage
                                    bi.setRGB(newX,newY, im.getRGB(i,j));
                                    newY++;
                                    
                                }
                                newY = 0;
                                newX++;
                            }
                          //sending new smaller image with subsampling applied
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            ImageIO.write(bi, att.getFileExtension(), stream);
                         //   c.sendFile(stream.toByteArray(), "subsampling: "+att.getFileName()).queue();
                        } else {
                            c.sendMessage("rate must be over 0 and under 8").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
                        }

                    } catch (Exception e) {
                        
                        e.printStackTrace();
                    }
                } else {
                    c.sendMessage("subsampling rate must be specified").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
                    message.delete().queue();
                    
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
