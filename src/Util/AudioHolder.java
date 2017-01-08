package Util;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class AudioHolder
{
    private static AudioPlayerManager apm;

    public static void init()
    {
        apm = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(apm);
    }

    public static AudioPlayerManager getManager()
    {
        return apm;
    }
}
