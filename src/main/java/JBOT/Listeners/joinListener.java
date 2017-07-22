package JBOT.Listeners;

import JBOT.Main;
import JBOT.Util.IO;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.io.File;
import java.io.IOException;

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
        System.out.println(Main.configs);
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
        System.out.println(Main.configs);
    }

}
