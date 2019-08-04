package JBOT.Commands;

import JBOT.Main;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.TrackSchedule;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class pause implements Command
{

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        TrackSchedule ts = Main.getGuildAudioPlayer(e.getGuild()).getSchedule();

        e.getMessage().delete().queue();

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
        return 1;
    }
}
