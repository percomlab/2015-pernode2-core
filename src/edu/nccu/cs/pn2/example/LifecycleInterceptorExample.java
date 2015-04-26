package edu.nccu.cs.pn2.example;

import org.apache.log4j.Logger;

import edu.nccu.cs.pn2.mom.Dispose;
import edu.nccu.cs.pn2.mom.Init;
import edu.nccu.cs.pn2.mom.ListeningTo;
import edu.nccu.cs.pn2.node.MqttNodeManager;
import edu.nccu.cs.pn2.node.MqttPerNode;
import edu.nccu.cs.pn2.node.NodeManager;

@ListeningTo("home/CONTEXT")
public class LifecycleInterceptorExample extends MqttPerNode
{
    private Logger log = Logger.getLogger(LifecycleInterceptorExample.class);

    public void processMessage(String topic, String message)
    {
        log.debug("incoming message: " + message);
        getSender().send("echo:" + message, "ssh.COMMAND");
    }

    @Init
    public void onStart()
    {
        log.debug("started.");
    }

    @Dispose
    public void onStop()
    {
        log.debug("stopped.");
    }

    public static void main(String[] args)
    {
        NodeManager ctx = new MqttNodeManager();
        ctx.setNode(new LifecycleInterceptorExample());
        ctx.start();

        try
        {
            Thread.sleep(10000);
        }
        catch (InterruptedException e)
        {
        }

        ctx.stop();
    }
}
