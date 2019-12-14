package JBOT.Commands;

import JBOT.Admin.playSilent;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class harambe implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        Command play = new playSilent();
        String[] arguments = {"playSilent", "https://www.youtube.com/watch?v=OJw3MmL-Omk"};
        play.run(e, arguments);
        e.getChannel().sendMessage("```we will never forget you, Harambe.```").queue();
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
