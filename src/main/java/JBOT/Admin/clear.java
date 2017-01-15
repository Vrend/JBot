package JBOT.Admin;

import JBOT.Main;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class clear implements Command
{

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        Main.getGuildAudioPlayer(e.getGuild()).getSchedule().clearQueue();
        e.getChannel().sendMessage("```Queue has been cleared```").queue();
    }

    @Override
    public int getPermLevel()
    {
        return 1;
    }
}
