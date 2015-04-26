/**
 * (2015) National Chengchi University, Department of Computer Science
 */
package edu.nccu.cs.pn2.node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.nccu.cs.pn2.mom.Broker;
import edu.nccu.cs.pn2.mom.Dispose;
import edu.nccu.cs.pn2.mom.Id;
import edu.nccu.cs.pn2.mom.Init;
import edu.nccu.cs.pn2.mom.ListeningTo;

public class MqttNodeManager implements BundleNodeManager
{
    private final static String DEFAULT_BROKER_URI = "tcp://localhost:1883";

    private Logger log = LoggerFactory.getLogger(MqttNodeManager.class);

    private MemoryPersistence persistence = new MemoryPersistence();

    private MqttAsyncClient subscriber;

    private MqttAsyncClient publisher;

    private MqttPerNode node;

    private String brokerUrl;

    private String listeningTopic;

    private Thread myThread;

    private String clientId;

    @Override
    public void setNode(PerNode n)
    {
        this.node = (MqttPerNode) n;
    }

    @Override
    public void start()
    {
        myThread = new Thread(this);
        myThread.start();
    }

    private void processAnnotations()
    {
        if (!node.getClass().isAnnotationPresent(Id.class))
        {
            clientId = node.getClass().getCanonicalName(); // default to the class's CanonicalName
        }
        else
            clientId = node.getClass().getAnnotation(Id.class).value();

        node.setName(clientId);
        log.info("Node Name: " + node.getName());

        // Broker URL
        if (!node.getClass().isAnnotationPresent(Broker.class))
        {
            // default tcp://localhost:1883
            brokerUrl = DEFAULT_BROKER_URI;
        } else
        {
            // obtain broker URL
            brokerUrl = node.getClass().getAnnotation(Broker.class).value();
        }

        if (!node.getClass().isAnnotationPresent(ListeningTo.class))
            listeningTopic = "pernode.null";
        else
        {
            listeningTopic = node.getClass().getAnnotation(ListeningTo.class).value();
            log.info("Listening Topic: " + listeningTopic);
        }

    }

    @Override
    public void run()
    {
        processAnnotations();

        log.info("MQTT Broker: " + brokerUrl);

        try
        {
            // setup publisher
            publisher = new MqttAsyncClient(brokerUrl, clientId + "-pub", persistence);
            MessageSender sender = new MqttMessageSender(publisher);
            node.setMessageSender(sender);

            // establish subscriber
            subscriber = new MqttAsyncClient(brokerUrl, clientId + "-sub", persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            subscriber.setCallback(node);
            // IMqttToken conToken = subscriber.connect(connOpts);
            // conToken.waitForCompletion();
            subscriber.connect(connOpts).waitForCompletion();
            IMqttToken subToken = subscriber.subscribe(listeningTopic, 2);// the qos is set to 2 so that it receives
                                                                          // messages of all qos level

            // find and invoke Init annotations
            Method[] ms = node.getClass().getMethods();
            for (Method m : ms)
            {
                if (m.isAnnotationPresent(Init.class))
                {
                    try
                    {
                        log.info("Invoking methods with @Init annotation: " + m.getName());
                        m.invoke(node, new Object[] {});
                    }
                    catch (IllegalArgumentException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            subToken.waitForCompletion();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void stop()
    {
        // find dispose annotation
        Method[] ms = node.getClass().getMethods();
        for (Method m : ms)
        {
            if (m.isAnnotationPresent(Dispose.class))
            {
                try
                {
                    log.info("Invoking methods with @Dispose annotation: " + m.getName());
                    m.invoke(node, new Object[] {});
                }
                catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                catch (InvocationTargetException e)
                {
                    e.printStackTrace();
                }
            }
        }

        // release MessageSender
        node.dispose();

        try
        {
            subscriber.disconnect();
        }
        catch (MqttException e)
        {
        }
    }

}
