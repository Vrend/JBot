package Commands;

import Util.AudioHolder;
import Util.BadCommandException;
import Util.Command;
import Util.TrackSchedule;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class cs implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        TrackSchedule ts = AudioHolder.getSchedule();
        String msg = ts.getSongInfo();

        e.getChannel().sendMessage(msg).queue();

    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
