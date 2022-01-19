package JBOT.Util;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Permissions
{
    public static boolean checkPermission(MessageReceivedEvent e, int lvl)
    {
        boolean isAdmin = isAdmin(e);
        boolean isOwner = isOwner(e);

        if(lvl == 0)
        {
            return true;
        }
        else if(lvl == 1 && (isAdmin || isOwner))
        {
            return true;
        }
        else if(lvl == 2 && isOwner)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isOwner(MessageReceivedEvent e)
    {
        String owner = IO.getKey("Keys/ownerid.key");
        return owner.equals(e.getMember().getUser().getId());
    }

    public static boolean isAdmin(MessageReceivedEvent e)
    {
        return e.getMember().getPermissions().contains(Permission.ADMINISTRATOR);
    }
}
