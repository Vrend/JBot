package JBOT.Commands;

import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class help implements Command
{

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        String msg = "```[A]autorole: Set default server role\n[A]avatar: Change bot picture\n[A]clear: Clears music queue\n[A]invite: Posts invite link\n[A]kick: Kicks user\n[A]kickrole: kicks users with role, using modifiers any for having the role, only for only having that role and nto for not having that role\n[A]nick: Change bots nickname\n[A]prune: Delete x number of messages\n[O]shutdown: Kills bot\ncrisps: Crisp sound\ncs: Current song\ndismiss: Make bot leave voice channel\nharambe: Play harambe song\nhelp: Help info\n[A]mute: Mutes bot\nnavy: Copy pasta\norange: Orange sound effect\norgasm: Meme sound effect\n[A]pause: Pause song\nplay: Plays song\npinkguy: plays his fire album\npussy: Pussy meme\nqueue: Displays queue\nsay: Parrots text\nskip: Skips song\nsummon: Summons bot to voice channel\n[A]volume: Change bot's volume```\n";
        e.getChannel().sendMessage(msg).queue();
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
