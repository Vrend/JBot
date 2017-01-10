package JBOT.Util;

import java.util.HashMap;

public class Vote
{
    private static HashMap<String, Vote> votes;

    private int voteCount;
    private int threshhold;

    public static void init()
    {
        votes = new HashMap<>();
    }

    public Vote()
    {
        voteCount = 0;
    }

    public int getLeft()
    {
        return (threshhold - voteCount);
    }

    public boolean addVote()
    {
        voteCount++;
        return (voteCount >= threshhold);
    }

    public static void newVote(String name)
    {
        votes.put(name, new Vote());
    }

    public static HashMap getList()
    {
        return votes;
    }

    public void setThresh(int num)
    {
        threshhold = num;
    }
}
