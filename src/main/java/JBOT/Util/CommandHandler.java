package JBOT.Util;

import JBOT.Main;

import java.io.File;
import java.util.ArrayList;

public class CommandHandler
{
    private ArrayList<String> cmds = new ArrayList<String>();
    private ArrayList<String> acmds = new ArrayList<String>();

    public CommandHandler()
    {
        loadCommands();
    }

    public void loadCommands()
    {

        if(Main.indev)
        {
            File admin = new File("src"+File.separator+"main"+File.separator+"java"+File.separator+"JBOT"+File.separator+"Admin");
            File commands = new File("src"+File.separator+"main"+File.separator+"java"+File.separator+"JBOT"+File.separator+"Commands");

            for (File f : admin.listFiles())
            {
                String s = f.getName();
                s = s.replace(".java", "");
                acmds.add(s);
            }
            for (File f : commands.listFiles())
            {
                String s = f.getName();
                s = s.replace(".java", "");
                cmds.add(s);
            }
        }
        else
        {
            ArrayList<String>[] lists = Main.getCommands();
            acmds = lists[0];
            cmds = lists[1];
        }

    }

    public Command getCommand(String name) throws BadCommandException
    {
        if(cmds.contains(name))
        {
            try
            {
                Class c = Class.forName("JBOT.Commands."+name);
                Command cmd = (Command) c.newInstance();
                return cmd;
            }
            catch (Exception e)
            {
                System.out.println("failed to get class");
                e.printStackTrace();
            }
        }
        else if(acmds.contains(name))
        {
            try
            {
                Class c = Class.forName("JBOT.Admin."+name);
                Command cmd = (Command) c.newInstance();
                return cmd;
            }
            catch (Exception e)
            {
                System.out.println("failed to get class");
                e.printStackTrace();
            }
        }
        else
        {
            throw new BadCommandException("```Malformed Command Request: Command not found```");
        }

        return null;
    }

}
