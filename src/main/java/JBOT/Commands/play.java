package JBOT.Commands;

import JBOT.Main;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.TrackSchedule;
import JBOT.Util.AudioHolder;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

public class play implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        if(e.getGuild().getAudioManager().getConnectedChannel() == null)
        {
            Command s = new summon();
            s.run(e, args);
        }

        if(args.length < 2)
        {
            throw new BadCommandException("Malformed Command Request: Improper Arguments");
        }

        String input = args[1];

        if(args.length > 2)
        {
            input = args[1];
            for (int i = 2; i < args.length; i++)
            {
                input += " ";
                input += args[i];
            }
        }

        AudioPlayerManager playerManager = Main.getManager();

        AudioHolder holder = Main.getGuildAudioPlayer(e.getGuild());

        TrackSchedule ts = holder.getSchedule();

        playerManager.loadItem(input, new AudioLoadResultHandler()
        {
                    @Override
                    public void trackLoaded(AudioTrack track)
                    {
                        String info = ts.queue(track);
                        e.getChannel().sendMessage(info).queue();
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist)
                    {
                        for (AudioTrack track : playlist.getTracks())
                        {
                            String info = ts.queue(track);
                            e.getChannel().sendMessage(info).queue();
                        }
                    }

                    @Override
                    public void noMatches()
                    {
                        e.getChannel().sendMessage(e.getMember().getAsMention()+": `No matches found.`").queue();
                    }

                    @Override
                    public void loadFailed(FriendlyException throwable)
                    {
                        e.getChannel().sendMessage(e.getMember().getAsMention()+": `Everything is exploding, kittens and children are massacred, and the world is ending. (Load failed)`").queue();
                    }
                });

        AudioManager am = e.getGuild().getAudioManager();

        am.setSendingHandler(holder.getSendHandler());
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
