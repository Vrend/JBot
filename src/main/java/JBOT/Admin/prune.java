package JBOT.Admin;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class prune implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        if(args.length != 2)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }

        try
        {
            MessageHistory mh = e.getChannel().getHistory();
            List<Message> messages = mh.retrievePast(Integer.parseInt(args[1])).complete();
            e.getTextChannel().deleteMessages(messages).queue();
        }
        catch(Exception e1)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }


    }

    @Override
    public int getPermLevel()
    {
        return 1;
    }
}
