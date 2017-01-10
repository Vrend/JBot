package JBOT.Util;


public class Time
{
    public static String parseTime(long input)
    {
       int secs = (int) input/1000;
       int hours = secs/60;
       secs = secs - (hours*60);
       if(secs < 10)
       {
           return (hours+":0"+secs);
       }

       return (hours+":"+secs);
    }
}
