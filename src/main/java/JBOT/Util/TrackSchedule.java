package JBOT.Util;

import JBOT.Main;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackSchedule extends AudioEventAdapter
{
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private static boolean paused = false;
    private static int vol;
    private static boolean muted = false;
    private Guild guild;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackSchedule(AudioPlayer player, Guild guild)
    {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.guild = guild;
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public String queue(AudioTrack track)
    {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.

        long dur = track.getDuration();
        String name = track.getInfo().title;

        String durs = Time.parseTime(dur);

        String output = "```"+name+" was added to the queue. [ "+durs+" ]```";

        if (!player.startTrack(track, true))
        {
            queue.offer(track);
        }

        return output;
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack()
    {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
//        player.startTrack(queue.poll(), false);

        AudioTrack track = queue.poll();
        if(track == null) {
            // No tracks left
            guild.getAudioManager().closeAudioConnection();
            clearQueue();
        }
        player.startTrack(track, false);

    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason)
    {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext)
        {
            nextTrack();
        }
    }

    public void clearQueue()
    {
        player.stopTrack();
        queue.clear();
        guild.getAudioManager().closeAudioConnection();
    }

    public void pause()
    {
        paused = !paused;
        player.setPaused(paused);
    }

    public boolean getpaused()
    {
        return paused;
    }

    public String getSongInfo()
    {
        String output;

        if(player.getPlayingTrack() != null)
        {
            long current = player.getPlayingTrack().getPosition();
            long end = player.getPlayingTrack().getDuration();

            String currents = Time.parseTime(current);
            String ends = Time.parseTime(end);
            String name = player.getPlayingTrack().getInfo().title;

            output = "```Currently playing: "+name+" [ "+currents+" : "+ends+" ]```";
        }
        else
        {
            output = "```No song currently playing```";
        }


        return output;
    }

    public boolean mute()
    {
        if(muted)
        {
            muted = false;
            player.setVolume(vol);
        }
        else
        {
            muted = true;
            vol = player.getVolume();
            player.setVolume(0);
        }

        return muted;
    }

    public void changeVolume(int volume)
    {
        player.setVolume(volume);
    }


    public String getQueue()
    {
        String res = "```";
        Object[] tracks = queue.toArray();

        AudioTrack playing = player.getPlayingTrack();

        if(playing != null)
        {
            long d = playing.getDuration();
            String ds = Time.parseTime(d);
            res += "1. "+playing.getInfo().title + " [ " + ds+" ]\n";


            for (int i = 0; i < tracks.length; i++)
            {
                AudioTrack track = (AudioTrack) tracks[i];
                long dur = track.getDuration();
                String durs = Time.parseTime(dur);

                res += (i+2)+". "+track.getInfo().title + " [ " + durs+" ]\n";
            }
            res += "```";
            return res;
        }

        return "";
    }

}