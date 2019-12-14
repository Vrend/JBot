package JBOT.Util;

import JBOT.Main;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
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
        catch(IOException e)
        {
            System.out.println("Failed to retrieve " + filename);
        }

        return output;
    }

    public static void addPasta(String guildId, String name, String content)
    {
        File pastaFolder = new File("configs/"+guildId+"/pastas");
        if(!pastaFolder.exists())
        {
            pastaFolder.mkdir();
        }

        File pastaFile = new File("configs/"+guildId+"/pastas/"+name);
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(pastaFile));
            bw.write(content);
            bw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String getPasta(String guildId, String name) throws IOException
    {
        File pastaFolder = new File("configs/"+guildId+"/pastas");
        File pastaFile = new File("configs/"+guildId+"/pastas/"+name);

        if(pastaFolder.exists() && pastaFile.exists())
        {
            return new String(Files.readAllBytes(pastaFile.toPath()));
        }

        return "";
    }

    public static ArrayList<String> getPastas(String guildId)
    {
        File pastaFolder = new File("configs/"+guildId+"/pastas");
        if(pastaFolder.exists() && pastaFolder.isDirectory())
        {
            ArrayList<String> pastas = new ArrayList<>();

            for(File file : pastaFolder.listFiles())
            {
                if(file.isFile())
                {
                    pastas.add(file.getName());
                }
            }
            return pastas;
        }
        return null;
    }


    public static HashMap<String, Boolean> getRooms(String guildId)
    {
        File roomFile = new File("configs/"+guildId+"/rooms");
        HashMap<String, Boolean> rooms = new HashMap<String, Boolean>();
        if(roomFile.exists())
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(roomFile));
                String line;
                while((line = br.readLine()) != null)
                {
                    String[] room = line.split(":");
                    if(room[1].trim().equals(""))
                    {
                        rooms.put(room[0].trim(), false);
                    }
                    else
                    {
                        rooms.put(room[0].trim(), true);
                    }
                }
                br.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        return rooms;
    }

    public static String[] getRoom(String guildId, String roomName)
    {
        if(!roomExists(guildId, roomName))
        {
            return null;
        }

        File roomFile = new File("configs/"+guildId+"/rooms");
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(roomFile));
            String line;
            while((line = br.readLine()) != null)
            {
                String[] candidateRoom = line.split(":");
                if(candidateRoom[0].equals(roomName))
                {
                    return candidateRoom;
                }
            }
            br.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void addRoom(String guildId, String roomName, String password, String user)
    {
        File roomFile = new File("configs/"+guildId+"/rooms");
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(roomFile, true));
            bw.write(roomName + ":" + password + ":" + user);
            bw.newLine();
            bw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteRoom(String guildId, String roomName)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            File roomFile = new File("configs/"+guildId+"/rooms");

            if(!roomFile.exists())
            {
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(roomFile));
            String line;
            while((line = br.readLine()) != null)
            {
                String roomNameCandidate = line.split(":")[0];
                if(!roomNameCandidate.equals(roomName))
                {
                    sb.append(line + "\n");
                }
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(roomFile));
            bw.write(sb.toString());
            bw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean roomExists(String guildId, String roomName) {
        try
        {
            File roomFile = new File("configs/"+guildId+"/rooms");

            if(!roomFile.exists())
            {
                return false;
            }
            BufferedReader br = new BufferedReader(new FileReader(roomFile));
            String line;
            while((line = br.readLine()) != null)
            {
                String roomNameCandidate = line.split(":")[0];
                if(roomNameCandidate.equals(roomName))
                {
                    br.close();
                    return true;
                }
            }
            br.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return false;
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

    public static void updateTerm(String guildid, String tag, String data)
    {
        File config = new File("configs/"+guildid+"/config");

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(config));
            String line;
            while((line = br.readLine()) != null)
            {
                String[] terms = line.split("=");
                if(tag.equals(terms[0]) && !data.equals(terms[1]))
                {
                    br.close();
                    String oldLine = terms[0]+"="+terms[1];
                    String newLine = tag+"="+data;

                    br = new BufferedReader(new FileReader(config));
                    String content = "";
                    while((line = br.readLine()) != null)
                    {
                        content += line;
                        content += "\n";
                    }
                    br.close();
                    content = content.replace(oldLine, newLine);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(config));
                    bw.write(content);
                    bw.close();
                    return;
                }
                else if(tag.equals(terms[0]) && data.equals(terms[1]))
                {
                    return;
                }
            }
            br.close();

            br = new BufferedReader(new FileReader(config));
            String content = "";
            while((line = br.readLine()) != null)
            {
                content += line;
                content += "\n";
            }
            br.close();
            content += tag+"="+data+"\n";

            BufferedWriter bw = new BufferedWriter(new FileWriter(config));
            bw.write(content);
            bw.close();

            HashMap<String, String> conf = Main.configs.get(guildid);
            conf.put(tag, data);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void removeTerm(String guildid, String tag)
    {
        File config = new File("configs/"+guildid+"/config");
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(config));
            String content = "";
            String line;
            String oldLine = "";
            while((line = br.readLine()) != null)
            {
                if(line.contains(tag))
                {
                    oldLine = line;
                }
                content += line;
                content += "\n";
            }
            content = content.replace(oldLine, "");
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(config));
            bw.write(content);
            bw.close();
            HashMap<String, String> vals = Main.configs.get(guildid);
            vals.remove("newplayer");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
