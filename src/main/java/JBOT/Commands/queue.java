package JBOT.Commands;

import JBOT.Main;
import JBOT.Util.AudioHolder;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.TrackSchedule;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class queue implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        AudioHolder holder = Main.getGuildAudioPlayer(e.getGuild());
        TrackSchedule ts = holder.getSchedule();
        String msg = ts.getQueue();
        e.getChannel().sendMessage(msg).queue();
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
