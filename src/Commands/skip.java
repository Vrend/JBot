package Commands;

import Util.AudioHolder;
import Util.BadCommandException;
import Util.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public class skip implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        AudioHolder.getSchedule().nextTrack();
        e.getChannel().sendMessage("`Skipping song....`").queue();
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
