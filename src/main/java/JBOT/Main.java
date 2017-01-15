package JBOT;

import JBOT.Util.AudioHolder;
import JBOT.Listeners.messageListener;
import JBOT.Util.IO;
import JBOT.Util.Vote;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;

import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Main
{
    private static ArrayList<String> acmds;
    private static ArrayList<String> cmds;

    public static boolean indev;

    private static AudioPlayerManager playerManager;
    private static Map<Long, AudioHolder> musicmanager;

    public static void main(String[] args)
    {
        String botapi;

        botapi = IO.getKey("Keys/botapi.key");

        acmds = new ArrayList<>();
        cmds = new ArrayList<>();

        if(args.length != 0)
        {
            if(args[0].equals("indev"))
            {
                System.out.println("Running in development environment");
                indev = true;
            }
        }
        else
        {
            indev = false;
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
        }

        Vote.init();

        init();

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


    public static void init()
    {
        musicmanager = new HashMap<>();
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    public static synchronized AudioHolder getGuildAudioPlayer(Guild guild)
    {
        long guildId = Long.parseLong(guild.getId());
        AudioHolder holder = musicmanager.get(guildId);

        if(holder == null)
        {
            holder = new AudioHolder(playerManager);
            musicmanager.put(guildId, holder);
        }

        return holder;
    }

    public static ArrayList[] getCommands()
    {
        ArrayList<String>[] lists = new ArrayList[2];

        lists[0] = acmds;
        lists[1] = cmds;

        return lists;
    }

    public static AudioPlayerManager getManager()
    {
        return playerManager;
    }

}
