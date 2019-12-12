package JBOT.Commands;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.IO;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class join implements Command {

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        String password = "";
        if(args.length < 2)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }
        else if(args.length > 2)
        {
            for (int i = 2; i < args.length; i++) {
                password += args[i];
            }
        }
        String roomName = args[1];
        if(e.getGuild().getRolesByName(roomName, true).size() < 1) {
            throw new BadCommandException("Malformed Command Request: Invalid Room");
        }
        String[] room = IO.getRoom(e.getGuild().getId(), roomName);
        if(room != null && (room[1].trim().equals("") || room[1].trim().equals(password)))
        {
            Role role = e.getGuild().getRolesByName(roomName, true).get(0);
            e.getGuild().getController().addRolesToMember(e.getMember(), role).queue();
            e.getMessage().delete().queue();
        }
        else
        {
            throw new BadCommandException("Malformed Command Request: Wrong Password");
        }
    }

    @Override
    public int getPermLevel() {
        return 0;
    }
}
