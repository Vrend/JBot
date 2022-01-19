package JBOT.Util;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command
{
    void run(MessageReceivedEvent e, String[] args) throws BadCommandException;

    int getPermLevel();
}
