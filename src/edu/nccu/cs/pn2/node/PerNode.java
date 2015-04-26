/**
 * (2015) National Chengchi University, Department of Computer Science
 */
package edu.nccu.cs.pn2.node;

/**
 * 
 * @author Chun-Feng Liao (2010.3.31) initial version
 *                        (2015.4.25) pernode2 based on MQTT  
 */
public interface PerNode 
{
    public void setMessageSender(MessageSender sender);

    public void processMessage(String fromTopic, String message);

    public String getName();
    
    public void setName(String name);
    
    public void dispose();
    
}
