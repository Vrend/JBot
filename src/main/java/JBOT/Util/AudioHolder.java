package JBOT.Util;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.core.entities.Guild;


public class AudioHolder {
    private AudioPlayer player;
    private TrackSchedule ts;

    public AudioHolder(AudioPlayerManager apm, Guild guild) {
        player = apm.createPlayer();
        ts = new TrackSchedule(player, guild);
        player.addListener(ts);
        player.setVolume(100);
    }

    public TrackSchedule getSchedule() {
        return ts;
    }

    public AudioPlayerSendHandler getSendHandler()
    {
        return new AudioPlayerSendHandler(player);
    }

}
