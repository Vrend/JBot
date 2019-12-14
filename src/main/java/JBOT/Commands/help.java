package JBOT.Commands;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class help implements Command
{

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        String msg = "```[A]autorole: Set default server role\n[A]avatar: Change bot picture\n[A]clear: Clears music queue\ncreate: Create a chat\ndelete: Delete a chat\n[A]invite: Posts invite link\njoin: Join a chat\n[A]kick: Kicks user\n[A]kickrole: Kicks users with role, using modifiers any for having the role, only for only having that role and not for not having that role\nleave: Leave a chat\nlist: List all chat channels\n[A]nick: Change bots nickname\n[A]prune: Delete x number of messages\n[O]shutdown: Kills bot\ncrisps: Crisp sound\ncs: Current song\ndismiss: Make bot leave voice channel\nharambe: Play harambe song\nhelp: Help info\n[A]mute: Mutes bot\nnavy: Copy pasta\norange: Orange sound effect\norgasm: Meme sound effect\n[A]pause: Pause song\nplay: Plays song\npinkguy: Plays his fire album\npussy: Pussy meme\nqueue: Displays queue\nsay: Parrots text\nskip: Skips song\nsummon: Summons bot to voice channel\n[A]volume: Change bot's volume```\n";
        e.getChannel().sendMessage(msg).queue();
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
