/**
 * (2015) National Chengchi University, Department of Computer Science
 */
package edu.nccu.cs.pn2.node;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public abstract class MqttPerNode implements PerNode, MqttCallback
{
    private MessageSender sender;

    private String name;

    @Override
    abstract public void processMessage(String fromTopic, String message);

    @Override
    final public void setMessageSender(MessageSender sender)
    {
        this.sender = sender;
    }
    
    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public void connectionLost(Throwable arg0)
    {
        // do nothing
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0)
    {
        // do nothing
    }

    @Override
    final public void messageArrived(String topic, MqttMessage mqttMessage)
    {
        processMessage(topic, mqttMessage.toString());
    }

    public MessageSender getSender()
    {
        return sender;
    }

    final public void dispose()
    {
        sender.dispose();
    }

    @Override
    final public String getName()
    {
//        if (name == null || name.isEmpty())
//            setName("http://percomlab.cs.nccu.edu.tw/pernode2/" + UUIDUtils.getUUID());
//        return name;
        return name;
    }

}
