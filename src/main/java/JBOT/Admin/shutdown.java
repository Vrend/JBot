package JBOT.Admin;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class shutdown implements Command
{

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        e.getMessage().delete().queue();
        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException exception)
        {
            System.out.println("Sleep failed...");
        }
        System.exit(0);
    }

    @Override
    public int getPermLevel()
    {
        return 2;
    }
}
