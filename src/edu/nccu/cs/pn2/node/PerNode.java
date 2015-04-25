/**
 * (2015) National Chengchi University, Department of Computer Science
 */
package edu.nccu.cs.pn2.node;

import org.eclipse.paho.client.mqttv3.MqttCallback;

/**
 * 
 * @author Chun-Feng Liao (2010.3.31) initial version
 *                        (2015.4.25) pernode2 based on MQTT  
 */
public interface PerNode extends MqttCallback
{
    public void setMessageSender(MessageSender sender);

    public void processMessage(String fromTopic, String message);

    public String getName();
    
    public void dispose();
    
}
