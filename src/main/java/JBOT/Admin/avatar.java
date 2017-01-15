package JBOT.Admin;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AccountManager;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class avatar implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        AccountManager manager = new AccountManager(e.getJDA().getSelfUser());

        e.getMessage().deleteMessage().queue();

        try
        {
            URL url = new URL(args[1]);
            InputStream is = new BufferedInputStream(url.openStream());
            Icon i = Icon.from(is);
            is.close();
            manager.setAvatar(i).queue();
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
