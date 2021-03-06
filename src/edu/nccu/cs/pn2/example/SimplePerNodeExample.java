package edu.nccu.cs.pn2.example;

import org.apache.log4j.Logger;

import edu.nccu.cs.pn2.mom.ListeningTo;
import edu.nccu.cs.pn2.node.MqttNodeManager;
import edu.nccu.cs.pn2.node.NodeManager;
import edu.nccu.cs.pn2.node.MqttPerNode;

/**
 * Receive a message from home/CONTEXT and then publish to home/COMMAND
 * If @Id is absent, the default is set to the Canonical Name of the class
 * If @Broker is absent, the default is set to tcp://localhost:1883
 * @author Chun-Feng Liao 
 */
@ListeningTo("home/CONTEXT")
public class SimplePerNodeExample extends MqttPerNode
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
