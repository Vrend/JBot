package JBOT.Admin;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;
import java.util.List;

public class kickrole implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        if (args.length < 3)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }

        String mod = args[1].trim();

        String role = "";
        for (int i = 2; i < args.length; i++)
        {
            role += args[i] + " ";
        }
        role = role.trim();

        List<Role> roles = e.getGuild().getRoles();
        Role kicking = null;
        boolean exists = false;
        for (Role r : roles)
        {
            if(r.getName().equals(role) && !r.isManaged())
            {
                exists = true;
                kicking = r;
                break;
            }
        }
        if(!exists)
        {
            throw new BadCommandException("Malformed Command Request: Not a Valid Role");
        }

        GuildController gc = e.getGuild().getController();

        if(mod.equals("any"))
        {
            List<Member> members = e.getGuild().getMembers();
            for(Member m : members)
            {
                if(m.getRoles().contains(kicking))
                {
                    gc.kick(m).queue();
                }
            }
        }
        else if(mod.equals("only"))
        {
            List<Member> members = e.getGuild().getMembers();
            for(Member m : members)
            {
                if((m.getRoles().contains(kicking)) && (m.getRoles().size() == 1))
                {
                    gc.kick(m).queue();
                }
            }
        }
        else if(mod.equals("not"))
        {
            List<Member> members = e.getGuild().getMembers();
            for(Member m : members)
            {
                if(!m.getRoles().contains(kicking))
                {
                    gc.kick(m).queue();
                }
            }
        }
        else
        {
            throw new BadCommandException("Malformed Command Request: Improper Modifier");
        }
    }

    @Override
    public int getPermLevel()
    {
        return 1;
    }
}
