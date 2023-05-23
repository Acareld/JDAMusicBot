package commands.picture;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
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
import net.dv8tion.jda.api.utils.FileUpload;

public class ConvolutionCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel c, Message message) {
        String[] args = message.getContentDisplay().substring(1).split(" ");
        
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
                    //Kernels for convolution
                    
                    //sharpen
                    Kernel sharp = new Kernel(3, 3, new float[] {0, -1, 0, -1, 5, -1, 0, -1, 0});
                    //edge detection
                    Kernel edge = new Kernel(3, 3, new float[] {-1, -1, -1, -1, 8, -1, -1, -1, -1});
                    //convolve op
                    ConvolveOp op;
                    
                    if(args.length >= 2) {
                        if(args[1].equalsIgnoreCase("sharpen")) {
                            op = new ConvolveOp(sharp);
                            op.filter(im, bi);
                        } else if(args[1].equalsIgnoreCase("edge")) {
                            op = new ConvolveOp(edge);
                            op.filter(im, bi);
                        } else {
                            c.sendMessage("unknown convolution type, use either \"sharpen\" or \"edge\"").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
                        }
                    } else {
                        c.sendMessage("please state a convolution type").complete().delete().queueAfter(1500, TimeUnit.MILLISECONDS);
                    }
                    
                    //sending new image with the convolution filter applied
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ImageIO.write(bi, att.getFileExtension(), stream);
                   // FileUpload file = new FileUpload(stream, "nein");
                  //  c.sendFile(stream.toByteArray(), "sepia: "+att.getFileName()).queue();
                    
                    
                    
                } catch(Exception e) {
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
