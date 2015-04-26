package edu.nccu.cs.pn2.node;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttMessageSender implements MessageSender
{
    static final private Logger log = Logger.getLogger(MessageSender.class);

    private MqttAsyncClient client;

    public MqttMessageSender(MqttAsyncClient publisher)
    {
        client = publisher;
        connect();
    }

    private void connect()
    {
        try
        {
//            IMqttToken token = client.connect();
//            token.waitForCompletion();
            client.connect().waitForCompletion();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void send(String topic, String message)
    {
        send(topic, message, 0);// default qos is 0
    }

    public void send(String topic, String message, int qos)
    {
        try
        {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(qos);
            client.publish(topic, mqttMessage);
            log.debug("publishing message: " + mqttMessage + " to [" + topic + "]");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void dispose()
    {
        try
        {
            client.disconnect();
        }
        catch (MqttException e)
        {
        }
    }

}
