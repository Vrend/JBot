package JBOT.Commands;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public class say implements Command
{

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        MessageChannel channel = e.getChannel();

        e.getMessage().deleteMessage().queue();

        String msg = "";

        if(args.length < 2)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }

        for (int i = 1; i < args.length; i++)
        {
            msg += args[i];
            msg += " ";
        }

        channel.sendMessage(msg).queue();
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
