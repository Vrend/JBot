package JBOT;

import JBOT.Util.AudioHolder;
import JBOT.Listeners.messageListener;
import JBOT.Util.IO;
import JBOT.Util.Vote;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Main
{
    private static ArrayList<String> acmds;
    private static ArrayList<String> cmds;

    public static void main(String[] args)
    {
        String botapi;

        botapi = IO.getKey("Keys/botapi.key");

        acmds = new ArrayList<>();
        cmds = new ArrayList<>();

        try
        {
            CodeSource source = Main.class.getProtectionDomain().getCodeSource();
            if(source != null)
            {
                URL jar = source.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                while(true)
                {
                    ZipEntry e = zip.getNextEntry();
                    if(e == null)
                    {
                        break;
                    }
                    else
                    {
                        String name = e.getName();

                         if(name.contains("JBOT/Admin"))
                         {
                             name = name.replace("JBOT/Admin/", "");
                             name = name.replace(".class", "");
                             acmds.add(name);
                         }
                         else if(name.contains("Command"))
                         {
                             name = name.replace("JBOT/Commands/", "");
                             name = name.replace(".class", "");
                             cmds.add(name);
                         }
                    }
                }

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        AudioHolder.init();

        Vote.init();

        try
        {
            JDA jda = new JDABuilder(AccountType.BOT).setToken(botapi).addListener(new messageListener()).buildBlocking();
        }
        catch(Exception e)
        {
            System.out.println("Error in JDA Initialization");
            System.exit(1);
        }

    }

    public static ArrayList[] getCommands()
    {
        ArrayList<String>[] lists = new ArrayList[2];

        lists[0] = acmds;
        lists[1] = cmds;

        return lists;
    }

}
