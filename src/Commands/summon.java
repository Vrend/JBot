package Commands;

import Util.BadCommandException;
import Util.Command;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

public class summon implements Command
{

    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {

        AudioManager am = e.getGuild().getAudioManager();

        VoiceChannel vc = e.getMember().getVoiceState().getChannel();

        if(vc != null)
        {
            am.openAudioConnection(vc);
        }
        else
        {
            throw new BadCommandException("Malformed Command Request: Not in voice channel");
        }


        e.getChannel().sendMessage("Greetings, master *"+e.getMember().getEffectiveName()+"*").queue();
    }

    @Override
    public int getPermLevel() {
        return 0;
    }
}
