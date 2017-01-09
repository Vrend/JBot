import Util.AudioHolder;
import Listeners.messageListener;
import Util.IO;
import Util.Vote;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.io.File;
import java.util.ArrayList;

public class Main
{

    public static void main(String[] args)
    {
        String botapi;

        botapi = IO.getKey("Keys"+ File.separator+"botapi.key");

        AudioHolder.init();

        Vote.init();

        try
        {
            JDA jda = new JDABuilder(AccountType.BOT).setToken(botapi).addListener(new messageListener()).buildBlocking();
        }
        catch(Exception e)
        {
            System.out.println("Error in JDA Initialization");
        }

    }

}
