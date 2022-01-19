package JBOT.Commands;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.IO;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class create implements Command {
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException {
        String password = "";
        if(args.length < 2) {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }
        else if(args[1].contains(":")) {
            throw new BadCommandException("Malformed Command Request: Invalid Room Name");
        }
        else if(args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                password += args[i];
            }
            if(password.contains(":"))
            {
                throw new BadCommandException("Malformed Command Request: Invalid Password");
            }
        }
        else
        {
            password = " ";
        }
        String roomName = args[1];
        if(IO.roomExists(e.getGuild().getId(), roomName))
        {
            throw new BadCommandException("Malformed Command Request: Room Already Exists");
        }

        e.getGuild().getController().createRole().setName(roomName).setMentionable(false).queue();
        Role everyone = e.getGuild().getPublicRole();
        Role newRole = null;
        while(newRole == null)
        {
            if(e.getGuild().getRolesByName(roomName, true).size() > 0)
            {
                newRole = e.getGuild().getRolesByName(roomName, true).get(0);
            }
        }
        long allow = Permission.MESSAGE_READ.getRawValue() | Permission.MESSAGE_MANAGE.getRawValue();
        long deny = Permission.MESSAGE_READ.getRawValue();
        e.getGuild().getController().createTextChannel(roomName).addPermissionOverride(everyone, 0, deny).addPermissionOverride(newRole, allow, 0).queue();
        e.getMessage().delete().queue();
        e.getChannel().sendMessage("```Successfully created channel " + roomName +  "```").queue();

        IO.addRoom(e.getGuild().getId(), roomName, password, e.getMember().getUser().getId());
        e.getGuild().getController().addRolesToMember(e.getMember(), newRole).queue();
    }

    @Override
    public int getPermLevel() {
        return 0;
    }
}
