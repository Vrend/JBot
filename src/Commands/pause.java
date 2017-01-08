package Commands;

import Util.AudioHolder;
import Util.BadCommandException;
import Util.Command;
import Util.TrackSchedule;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class pause implements Command
{

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        TrackSchedule ts = AudioHolder.getSchedule();

        e.getMessage().deleteMessage().queue();

        if (ts.getpaused())
        {
            e.getChannel().sendMessage("`Resuming song`").queue();
        }
        else
        {
            e.getChannel().sendMessage("`Pausing song`").queue();
        }
        ts.pause();
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
