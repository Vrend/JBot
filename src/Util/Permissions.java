package Util;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;

public class Permissions
{
    public static boolean checkPermission(MessageReceivedEvent e, int lvl)
    {
        boolean isAdmin = false;
        boolean isOwner = checkOwner(e.getMember().getUser().getId());

        if(e.getMember().getPermissions().contains(Permission.ADMINISTRATOR))
        {
            isAdmin = true;
        }

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

    public static boolean checkOwner(String id)
    {
        String owner = IO.getKey("Keys"+ File.separator+"owneruser.key");
        return id.equals(owner);
    }
}
