package JBOT.Admin;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

public class kick implements Command
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
            name += args[i] + " ";
        }
        name = name.trim();

        GuildController gc = e.getGuild().getController();

        for(Member m : e.getGuild().getMembers())
        {
            if(m.getEffectiveName().equals(name))
            {
                gc.kick(m).queue();
                e.getChannel().sendMessage("```Kicking user: "+m.getEffectiveName()+"...```").queue();
                return;
            }
        }

        throw new BadCommandException("Malformed Command Request: Invalid User");
    }

    @Override
    public int getPermLevel()
    {
        return 1;
    }
}
