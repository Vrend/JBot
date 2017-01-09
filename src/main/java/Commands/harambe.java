package Commands;

import Util.BadCommandException;
import Util.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class harambe implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        Command play = new play();
        String[] arguments = {"play", "https://www.youtube.com/watch?v=OJw3MmL-Omk"};
        play.run(e, arguments);
        e.getChannel().sendMessage("`we will never forget you, Harambe.`").queue();
    }

    @Override
    public int getPermLevel()
    {
        return 1;
    }
}
