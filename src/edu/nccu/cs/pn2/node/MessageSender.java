/**
 * (2015) National Chengchi University, Department of Computer Science
 */
package edu.nccu.cs.pn2.node;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MessageSender
{
    static final private Logger log = Logger.getLogger(MessageSender.class);

    private MqttAsyncClient client;

    public MessageSender(MqttAsyncClient publisher)
    {
        client = publisher;
        connect();
    }

    private void connect()
    {
        try
        {
            IMqttToken token = client.connect();
            token.waitForCompletion();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void send(String topic, String message)
    {
        try
        {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(0);// qos set to 0
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
