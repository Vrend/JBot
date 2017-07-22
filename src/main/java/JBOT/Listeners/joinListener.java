package JBOT.Listeners;

import JBOT.Main;
import JBOT.Util.IO;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class joinListener extends ListenerAdapter
{
    public void onGuildJoin(GuildJoinEvent event)
    {
        System.out.println("joined guild with id "+event.getGuild().getId());
        super.onGuildJoin(event);
        String id = event.getGuild().getId();

        File dir = new File("configs/"+id);
        if(!dir.isDirectory())
        {
            dir.mkdir();
            File config = new File("configs/"+id+"/config");
            try
            {
                config.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        Main.configs.put(id, IO.getConfig(id));
    }

    public void onGuildLeave(GuildLeaveEvent event)
    {
        System.out.println("left guild with id "+event.getGuild().getId());
        super.onGuildLeave(event);
        String id = event.getGuild().getId();
        File dir = new File("configs/"+id);
        if(dir.exists())
        {
            File config = new File("configs/"+id+"/config");
            config.delete();
            dir.delete();
        }

        Main.configs.remove(id);
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        super.onGuildMemberJoin(event);
        System.out.println("User joined with the name "+event.getMember().getEffectiveName()+" and id "+event.getMember().getUser().getId());
        String roleName = Main.configs.get(event.getGuild().getId()).get("newplayer");
        Role needed = null;
        if(roleName != null)
        {
            GuildController gc = event.getGuild().getController();
            List<Role> roles = event.getGuild().getRoles();

            for(Role r : roles)
            {
                if(r.getName().equals(roleName))
                {
                    needed = r;
                    break;
                }
            }
            if(needed != null)
            {
                gc.addRolesToMember(event.getMember(), needed).queue();
            }
        }
    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event)
    {
        super.onGuildMemberLeave(event);
        System.out.println("User left with the name "+event.getMember().getEffectiveName()+" and id "+event.getMember().getUser().getId());
    }

}
