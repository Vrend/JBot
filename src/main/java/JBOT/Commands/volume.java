package JBOT.Commands;

import JBOT.Main;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.TrackSchedule;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class volume implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        if(args.length != 2)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }

        TrackSchedule ts = Main.getGuildAudioPlayer(e.getGuild()).getSchedule();


        try
        {
            int num = Integer.parseInt(args[1]);
            ts.changeVolume(num);
        }
        catch(Exception ex)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }


    }

    @Override
    public int getPermLevel()
    {
        return 1;
    }
}
