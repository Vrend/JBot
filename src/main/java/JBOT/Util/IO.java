package JBOT.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class IO
{
    public static String getKey(String filename)
    {
        String output = "";
        try
        {
            File input = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(input));
            output = br.readLine();
            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return output;
    }
}
