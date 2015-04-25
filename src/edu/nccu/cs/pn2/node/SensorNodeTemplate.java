/**
 * (2015) National Chengchi University, Department of Computer Science
 */
package edu.nccu.cs.pn2.node;

import edu.nccu.cs.pn2.mom.Init;

public abstract class SensorNodeTemplate extends PerNodeBase implements Runnable
{
    private Thread communicatingThread;

    public SensorNodeTemplate()
    {
        super();
    }
    
    @Init
    final public void init()
    {
        communicatingThread = new Thread(this);
        communicatingThread.start();
    }
    
    final public void run()
    {
        sensorMain();
    }
    
    public abstract void sensorMain();

    final public void processMessage(String topic, String message) {};
}
