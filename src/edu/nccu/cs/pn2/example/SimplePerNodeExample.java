package edu.nccu.cs.pn2.example;

import org.apache.log4j.Logger;

import edu.nccu.cs.pn2.mom.Broker;
import edu.nccu.cs.pn2.mom.Id;
import edu.nccu.cs.pn2.mom.ListeningTo;
import edu.nccu.cs.pn2.node.MqttNodeManager;
import edu.nccu.cs.pn2.node.NodeManager;
import edu.nccu.cs.pn2.node.PerNodeBase;

/**
 * Receive a message from home/CONTEXT and then publish to home/COMMAND
 * @author Chun-Feng Liao 
 */
@Id("http://percomlab.cs.nccu.edu.tw/pernode2/sample1")
@Broker("tcp://localhost:1883")
@ListeningTo("home/CONTEXT")
public class SimplePerNodeExample extends PerNodeBase
{
    private Logger log = Logger.getLogger(SimplePerNodeExample.class);

    public static void main(String[] args)
    {
        NodeManager ctx = new MqttNodeManager();
        ctx.setNode(new SimplePerNodeExample());
        ctx.start();
    }

    @Override
    public void processMessage(String fromTopic, String message)
    {
        log.debug("incoming message: " + message);
        getSender().send("home/COMMAND", "echo: " + message);
    }
}
