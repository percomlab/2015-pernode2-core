package edu.nccu.cs.pn2.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.nccu.cs.pn2.mom.Broker;
import edu.nccu.cs.pn2.mom.Id;
import edu.nccu.cs.pn2.mom.ListeningTo;
import edu.nccu.cs.pn2.node.MqttNodeManager;
import edu.nccu.cs.pn2.node.NodeManager;
import edu.nccu.cs.pn2.node.SensorNodeTemplate;
@Id
@Broker
public class SensorNodeExample extends SensorNodeTemplate
{
    Logger log = LoggerFactory.getLogger(SensorNodeExample.class);

    public static void main(String[] args)
    {
        NodeManager ctx = new MqttNodeManager();
        ctx.setNode(new SensorNodeExample());
        ctx.start();
    }

    @Override
    public void sensorMain()
    {
        log.info("Hello World.");
        // reading some values from sensor
        // publishing the values
        getSender().send("home/CONTEXT", "some values...");
    }

}
