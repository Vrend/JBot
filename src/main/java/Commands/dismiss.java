package Commands;

import Util.AudioHolder;
import Util.BadCommandException;
import Util.Command;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

public class dismiss implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        AudioManager am = e.getGuild().getAudioManager();
        am.closeAudioConnection();

        AudioHolder.getSchedule().clearQueue();

        e.getChannel().sendMessage("It seems my services are needed elsewhere. Good day.").queue();
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
