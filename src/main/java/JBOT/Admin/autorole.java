package JBOT.Admin;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.IO;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class autorole implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        if(args.length < 2)
        {
            IO.removeTerm(e.getGuild().getId(), "newplayer");
            e.getChannel().sendMessage("```Clearing auto role...```").queue();
            return;
        }

        String roleName = "";
        for (int i = 1; i < args.length; i++)
        {
            roleName += args[i];
            roleName += " ";
        }
        roleName = roleName.trim();

        List<Role> roles = e.getGuild().getRoles();

        for(Role r : roles)
        {
            if(r.getName().equals(roleName))
            {
                if(!r.isManaged())
                {
                    IO.updateTerm(e.getGuild().getId(), "newplayer", roleName);
                    e.getChannel().sendMessage("```Setting auto role to "+roleName+"....```").queue();
                }
                else
                {
                    throw new BadCommandException("Malformed Command Request: Role is Managed");
                }
            }
        }

    }

    @Override
    public int getPermLevel()
    {
        return 1;
    }
}
