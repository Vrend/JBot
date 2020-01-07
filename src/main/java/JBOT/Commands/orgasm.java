package JBOT.Commands;

import JBOT.Admin.playSilent;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class orgasm implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        Command play = new playSilent();
        String[] arguments = {"playSilent", "https://www.youtube.com/watch?v=dQVD6PxdqT0"};
        play.run(e, arguments);
    }

    @Override
    public int getPermLevel() {
        return 0;
    }
}
