package JBOT.Commands;

import JBOT.Main;
import JBOT.Util.AudioHolder;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.TrackSchedule;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.FunctionalResultHandler;
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

        String finalInput = input;
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
                        int i = 0;
                        for (AudioTrack track : playlist.getTracks())
                        {
                            String info = ts.queue(track);
                            i++;
                        }

                        String msg = "```Added "+i+" songs to the queue```";
                        e.getChannel().sendMessage(msg).queue();
                    }

                    @Override
                    public void noMatches()
                    {
                        playerManager.loadItem("ytsearch: " + finalInput, new FunctionalResultHandler(null, playlist -> {
                            String info = ts.queue(playlist.getTracks().get(0));
                            e.getChannel().sendMessage(info).queue();
                        }, () -> e.getChannel().sendMessage(e.getMember().getAsMention()+": No matches found.").queue(), null ));
                    }

                    @Override
                    public void loadFailed(FriendlyException throwable)
                    {
                        throwable.printStackTrace();
                        e.getChannel().sendMessage("```" + e.getMember().getAsMention()+": Everything is exploding, kittens and children are massacred, and the world is ending. (Load failed)```").queue();
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
