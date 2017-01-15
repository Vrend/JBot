package JBOT.Admin;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

public class nick implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        if(args.length < 2)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }

        String name = "";

        for (int i = 1; i < args.length; i++)
        {
                name += args[i];
                name += " ";
        }

        GuildController gm = e.getGuild().getController();
        gm.setNickname(e.getGuild().getSelfMember(), name).queue();

    }

    @Override
    public int getPermLevel()
    {
        return 1;
    }
}
