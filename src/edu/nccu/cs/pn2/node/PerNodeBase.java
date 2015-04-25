/**
 * (2015) National Chengchi University, Department of Computer Science
 */
package edu.nccu.cs.pn2.node;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import edu.nccu.cs.pn2.mom.Id;
import edu.nccu.cs.pn2.utils.UUIDUtils;

public abstract class PerNodeBase implements PerNode
{
    private MessageSender sender;

    @Override
    abstract public void processMessage(String fromTopic, String message);

    @Override
    final public void setMessageSender(MessageSender sender)
    {
        this.sender = sender;
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
        Id id = this.getClass().getAnnotation(Id.class);
        if (id != null)
            return id.value();
        else
            return "http://percomlab.cs.nccu.edu.tw/pernode2/" + UUIDUtils.getUUID();
    }

}
