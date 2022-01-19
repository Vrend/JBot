package JBOT.Commands;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.IO;
import JBOT.Util.Permissions;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class delete implements Command {
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        if(args.length < 2)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }

        String roomName = args[1];
        String[] room = IO.getRoom(e.getGuild().getId(), roomName);
        if(room != null && ((e.getMember().getUser().getId().equals(room[2].trim())) || Permissions.isAdmin(e) || Permissions.isOwner(e)))
        {
            if(e.getGuild().getTextChannelsByName(roomName, true).size() > 0)
            {
                e.getGuild().getTextChannelsByName(roomName, true).get(0).delete().queue();
            }
            if(e.getGuild().getRolesByName(roomName, true).size() > 0)
            {
                e.getGuild().getRolesByName(roomName, true).get(0).delete().queue();
            }
            IO.deleteRoom(e.getGuild().getId(), roomName);
            e.getMessage().delete().queue();
            e.getChannel().sendMessage("```Successfully deleted channel "+roomName+"```").queue();
            return;
        }


        throw new BadCommandException("Malformed Command Request: Improper Arguments");


    }

    @Override
    public int getPermLevel() {
        return 0;
    }
}
