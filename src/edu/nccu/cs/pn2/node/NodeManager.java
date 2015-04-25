/**
 * (2015) National Chengchi University, Department of Computer Science
 */
package edu.nccu.cs.pn2.node;

/**
 * @author Chun-Feng Liao (2015/4/25)
 *
 */
public interface NodeManager extends Runnable
{
    public void setNode(PerNode node);
    
    public void start();

    public void stop();
}
