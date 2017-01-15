package JBOT.Commands;

import JBOT.Main;
import JBOT.Util.BadCommandException;
import JBOT.Util.Command;
import JBOT.Util.Permissions;
import JBOT.Util.Vote;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class skip implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        if(Permissions.checkOwner(e.getMember().getUser().getId()))
        {
            Main.getGuildAudioPlayer(e.getGuild()).getSchedule().nextTrack();
            e.getChannel().sendMessage("```Skipping song....```").queue();
        }
        else
        {
            if(Vote.getList().get("skip"+e.getGuild().getId()) == null)
            {
                Vote.newVote("skip"+e.getGuild().getId());
            }
            Vote v = (Vote) Vote.getList().get("skip"+e.getGuild().getId());
            double th = e.getMember().getVoiceState().getChannel().getMembers().size();
            th--;
            th = Math.ceil(th/2.0);
            int thresh = (int) th;
            v.setThresh(thresh);

            if(v.addVote())
            {
                e.getChannel().sendMessage("```Skipping song....```").queue();
                Main.getGuildAudioPlayer(e.getGuild()).getSchedule().nextTrack();
                Vote.getList().remove("skip"+e.getGuild().getId());
            }
            else
            {
                String msg = "```Vote acknowledged, the song will be skipped in " +v.getLeft()+" votes.```";
                e.getChannel().sendMessage(msg).queue();
            }
        }
    }

    @Override
    public int getPermLevel()
    {
        return 0;
    }
}
