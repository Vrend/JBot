package Listeners;

import Util.BadCommandException;
import Util.Command;
import Util.CommandHandler;
import Util.Permissions;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class messageListener extends ListenerAdapter
{
    @Override
    public void onMessageReceived(MessageReceivedEvent e)
    {
        JDA jda = e.getJDA();
        long resnum = e.getResponseNumber();

        User author = e.getAuthor();

        if(e.isFromType(ChannelType.TEXT))
        {
            Guild g = e.getGuild();
            TextChannel tc = e.getTextChannel();
            String name = e.getMember().getEffectiveName();

            System.out.printf("(%s)[%s]<%s>: %s\n", g.getName(), tc.getName(), name, e.getMessage().getContent());
        }
        else if(e.isFromType(ChannelType.PRIVATE))
        {
            PrivateChannel pvc = e.getPrivateChannel();

            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), e.getMessage().getContent());
        }
        else
        {
            System.out.println("Error in message catching. Please repair net.");
        }

        if(e.isFromType(ChannelType.TEXT))
        {
            if(e.getMessage().getContent() != null)
            {
                String msg = e.getMessage().getContent();

                String[] args = msg.split(" ");

                if(args[0].startsWith("'"))
                {
                    CommandHandler cmds = new CommandHandler();

                    Command req = null;

                    try
                    {
                        req = cmds.getCommand(args[0].substring(1));
                    }
                    catch (BadCommandException e1)
                    {
                        System.out.println(e1.getMessage());
                        MessageChannel channel = e.getChannel();
                        String name = e.getMember().getAsMention();
                        channel.sendMessage(name+": `"+e1.getMessage()+"`").queue();
                    }

                    if(req != null)
                    {
                        try
                        {
                            if(Permissions.checkPermission(e, req.getPermLevel()))
                            {
                                req.run(e, args);
                            }
                            else
                            {
                                MessageChannel mc = e.getChannel();
                                String name = e.getMember().getAsMention();
                                mc.sendMessage(name+": `You don't have the required permissions to run this`").queue();
                            }

                        }
                        catch (BadCommandException e1)
                        {
                            System.out.println(e1.getMessage());

                            MessageChannel channel = e.getChannel();
                            String name = e.getMember().getAsMention();
                            channel.sendMessage(name+": `"+e1.getMessage()+"`").queue();

                        }
                    }

                }
            }
        }
    }
}
