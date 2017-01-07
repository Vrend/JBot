package Util;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Permissions
{
    public static boolean checkPermission(MessageReceivedEvent e, int lvl)
    {
        boolean isAdmin = false;
        boolean isOwner = false;

        if(e.getMember().getPermissions().contains(Permission.ADMINISTRATOR))
        {
            isAdmin = true;
        }

        String owner = IO.getKey("Keys/owneruser.key");

        if(e.getMember().getUser().getName().equals(owner))
        {
            isOwner = true;
        }

        if(lvl == 0)
        {
            return true;
        }
        else if(lvl == 1 && isAdmin)
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
}
