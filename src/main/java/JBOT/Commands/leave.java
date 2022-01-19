package JBOT.Commands;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class leave implements Command {

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        if(args.length < 2)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }

        String roomName = args[1];
        if(e.getGuild().getRolesByName(roomName, true).size() > 0)
        {
            Role role = e.getGuild().getRolesByName(roomName, true).get(0);
            if(e.getMember().getRoles().contains(role))
            {
                e.getGuild().getController().removeSingleRoleFromMember(e.getMember(), role).queue();
                e.getMessage().delete().queue();
                return;
            }
        }

        throw new BadCommandException("Malformed Command Request: Improper Arguments");
    }

    @Override
    public int getPermLevel() {
        return 0;
    }
}
