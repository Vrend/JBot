package JBOT.Commands;

import JBOT.Admin.playSilent;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.IO;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.io.IOException;

public class pasta implements Command {
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        if(args.length < 2)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }
        else if(args.length > 2)
        {
            String content = "";
            if(args[2].toLowerCase().trim().equals("video")) { // video meme
                for (int i = 3; i < args.length; i++)
                {
                    content += args[i] + " ";
                }

                if(content.trim().equals(""))
                {
                    throw new BadCommandException("Malformed Command Request: No Pasta Text Detected");
                }
                IO.addPasta(e.getGuild().getId(), args[1], "video " + content.trim());
                e.getChannel().sendMessage("```Created video pasta called: "+ args[1] + "```").queue();
            }
            else { // text pasta
                for (int i = 2; i < args.length; i++)
                {
                    content += args[i] + " ";
                }

                if(content.trim().equals(""))
                {
                    throw new BadCommandException("Malformed Command Request: No Pasta Text Detected");
                }
                IO.addPasta(e.getGuild().getId(), args[1], content.trim());
                e.getChannel().sendMessage("```Created pasta called: "+ args[1] + "```").queue();
            }



        }
        else
        {
            try
            {
                String content = IO.getPasta(e.getGuild().getId(), args[1]);
                if(content.trim().equals(""))
                {
                    throw new BadCommandException("Malformed Command Request: Pasta Not Found");
                }
                String[] words = content.split(" ");
                if(words[0].trim().equals("video")) {
                    Command play = new playSilent();
                    String[] arguments = {"playSilent", words[1]};
                    play.run(e, arguments);
                }
                else {
                    e.getChannel().sendMessage(content).queue();
                }
            }
            catch(IOException exception)
            {
                throw new BadCommandException("Malformed Command Request: Improper Arguments");
            }
        }
        e.getMessage().delete().queue();
    }

    @Override
    public int getPermLevel() {
        return 0;
    }
}
