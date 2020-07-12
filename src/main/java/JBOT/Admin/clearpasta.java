package JBOT.Admin;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.IO;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class clearpasta implements Command {

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        if(args.length < 2) {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }

        boolean result = IO.deletePasta(e.getGuild().getId(), args[1]);
        if(!result) {
            e.getChannel().sendMessage("```Failed to delete " + args[1] + ". Pasta may not exist.```").queue();
            return;
        }
        e.getMessage().delete().queue();
        e.getChannel().sendMessage("```Pasta " + args[1] + " deleted```").queue();
    }

    @Override
    public int getPermLevel() {
        return 1;
    }
}
