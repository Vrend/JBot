import Util.AudioHolder;
import Listeners.messageListener;
import Util.IO;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Main
{

    public static void main(String[] args)
    {
        String botapi;

        botapi = IO.getKey("Keys/botapi.key");

        AudioHolder.init();

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
