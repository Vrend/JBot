package JBOT.Commands;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.IO;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class pastalist implements Command {
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        String output = "```PASTA LIST:\n";
        ArrayList<String> pastas = IO.getPastas(e.getGuild().getId());
        if(pastas == null)
        {
            e.getChannel().sendMessage("```No pastas have been created.```").queue();
            return;
        }
        for(String pasta : pastas)
        {
            output += "*  " + pasta + "\n";
        }
        output += "```";
        e.getChannel().sendMessage(output).queue();
        e.getMessage().delete().queue();
    }

    @Override
    public int getPermLevel() {
        return 0;
    }
}
