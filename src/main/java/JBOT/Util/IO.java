package JBOT.Util;

import JBOT.Main;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IO
{
    public static String getKey(String filename)
    {
        String output = "";
        try
        {
            File input = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(input));
            output = br.readLine();
            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return output;
    }

    public static HashMap getConfig(String id)
    {
        HashMap<String, String> map = new HashMap<>();

        try
        {
            File config = new File("configs/"+id+"/config");
            BufferedReader br = new BufferedReader(new FileReader(config));
            String line;
            while((line = br.readLine()) != null)
            {
                if(line.equals(""))
                {
                    continue;
                }
                String[] arg = line.split("=");
                map.put(arg[0], arg[1]);
            }
            br.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return map;
    }

    public static HashMap getConfigs()
    {
        HashMap<String, HashMap<String, String>> configs = new HashMap<>();

        File dir = new File("configs");
        if(!dir.exists())
        {
            dir.mkdir();
        }

        for(File config : dir.listFiles())
        {
            HashMap<String, String> map = getConfig(config.getName());
            configs.put(config.getName(), map);
        }

        return configs;
    }

    public static void updateConfigs(JDA jda)
    {
        List<Guild> guilds = jda.getGuilds();

        File dir = new File("configs");

        File[] temp = dir.listFiles();
        List<File> files = Arrays.asList(temp);

        boolean exists;
        for(Guild g : guilds)
        {
            exists = false;
            for(int i = 0; i < files.size(); i++)
            {
                File f = files.get(i);
                if(f == null)
                {
                    continue;
                }
                if(f.getName().equals(g.getId()))
                {
                    exists = true;
                    files.set(i, null);
                    break;
                }
            }
            if(!exists)
            {
                String id = g.getId();
                File newserver = new File("configs/"+id);
                newserver.mkdir();
                File newconfig = new File("configs/"+id+"/config");
                try
                {
                    newconfig.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        for(File f : files)
        {
            if(f == null)
            {
                continue;
            }
            File config = new File(f.getPath()+"/config");
            config.delete();
            f.delete();
        }

        Main.configs = getConfigs();
    }

}
