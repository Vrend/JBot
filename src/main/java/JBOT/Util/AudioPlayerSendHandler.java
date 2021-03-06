package JBOT.Util;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.core.audio.AudioSendHandler;

/*
* Wrapper used to interface between lavaplayer and JDA
*
* This just replaces JDA's AudioSendHandler with lavaplayer's
*
*
* Copied and pasted from lavaplayer's README
* */

public class AudioPlayerSendHandler implements AudioSendHandler
{
    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    public AudioPlayerSendHandler(AudioPlayer audioPlayer)
    {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public boolean canProvide()
    {
        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    @Override
    public byte[] provide20MsAudio()
    {
        return lastFrame.getData();
    }

    @Override
    public boolean isOpus()
    {
        return true;
    }
}