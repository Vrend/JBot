package Util;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;


public class AudioHolder
{
    private static AudioPlayerManager apm;
    private static AudioPlayer player;
    private static TrackSchedule ts;

    public static void init()
    {
        apm = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(apm);
        player = apm.createPlayer();
        ts = new TrackSchedule(player);
        player.addListener(ts);
        player.setVolume(25);
    }

    public static AudioPlayerManager getManager()
    {
        return apm;
    }

    public static TrackSchedule getSchedule()
    {
        return ts;
    }

    public static AudioPlayer getPlayer()
    {
        return player;
    }
}
