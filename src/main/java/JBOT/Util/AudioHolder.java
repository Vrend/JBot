package JBOT.Util;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;


public class AudioHolder {
    private AudioPlayer player;
    private TrackSchedule ts;

    public AudioHolder(AudioPlayerManager apm) {
        player = apm.createPlayer();
        ts = new TrackSchedule(player);
        player.addListener(ts);
        player.setVolume(25);
    }

    public TrackSchedule getSchedule() {
        return ts;
    }

    public AudioPlayerSendHandler getSendHandler()
    {
        return new AudioPlayerSendHandler(player);
    }

}
