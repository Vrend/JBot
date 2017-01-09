package Commands;

import Util.AudioHolder;
import Util.BadCommandException;
import Util.Command;
import Util.TrackSchedule;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class mute implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        TrackSchedule ts = AudioHolder.getSchedule();
        boolean b = ts.mute();
        if(b)
        {
            e.getChannel().sendMessage("`Music has been muted.`").queue();
        }
        else
        {
            e.getChannel().sendMessage("`Music has been unmuted.`").queue();
        }
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
