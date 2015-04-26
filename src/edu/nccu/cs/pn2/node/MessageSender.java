/**
 * (2015) National Chengchi University, Department of Computer Science
 */
package edu.nccu.cs.pn2.node;

public interface MessageSender
{
    public void send(String topic, String message);
    public void dispose();
}
