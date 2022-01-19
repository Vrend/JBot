package JBOT.Commands;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.IO;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class list implements Command {
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        String output = "```PUBLIC CHATS\n";
        HashMap<String, Boolean> rooms = IO.getRooms(e.getGuild().getId());
        for(String room : rooms.keySet())
        {
            if(!rooms.get(room))
            {
                output += "*  " + room + "\n";
            }
        }
        output += "\nPRIVATE CHATS\n";
        for(String room : rooms.keySet())
        {
            if(rooms.get(room))
            {
                output += "*  " + room + "\n";
            }
        }
        output += "```";
        e.getChannel().sendMessage(output).queue();
    }

    @Override
    public int getPermLevel() {
        return 0;
    }
}
