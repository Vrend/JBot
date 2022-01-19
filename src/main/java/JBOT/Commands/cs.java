package JBOT.Commands;

import JBOT.Main;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.TrackSchedule;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class cs implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        TrackSchedule ts = Main.getGuildAudioPlayer(e.getGuild()).getSchedule();
        String msg = ts.getSongInfo();

        e.getChannel().sendMessage(msg).queue();

    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
