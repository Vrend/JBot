package Commands;

import Util.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class skip implements Command
{
    @Override
    public void run(MessageReceivedEvent e, String[] args) throws BadCommandException
    {
        if(Permissions.checkOwner(e.getMember().getUser().getId()))
        {
            AudioHolder.getSchedule().nextTrack();
            e.getChannel().sendMessage("`Skipping song....`").queue();
        }
        else
        {
            if(Vote.getList().get("skip") == null)
            {
                Vote.newVote("skip");
            }
            Vote v = (Vote) Vote.getList().get("skip");
            double th = e.getMember().getVoiceState().getChannel().getMembers().size();
            th--;
            th = Math.ceil(th/2.0);
            int thresh = (int) th;
            System.out.println(th);
            v.setThresh(thresh);

            if(v.addVote())
            {
                e.getChannel().sendMessage("`Skipping song....`").queue();
                AudioHolder.getSchedule().nextTrack();
                Vote.getList().remove("skip");
            }
            else
            {
                String msg = "`Vote acknowledged, the song will be skipped in " +v.getLeft()+" votes.`";
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
