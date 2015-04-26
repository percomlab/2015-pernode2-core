package edu.nccu.cs.pn2.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import edu.nccu.cs.pn2.mom.ListeningTo;
import edu.nccu.cs.pn2.node.MqttNodeManager;
import edu.nccu.cs.pn2.node.MqttPerNode;
import edu.nccu.cs.pn2.node.NodeManager;
import edu.nccu.cs.pn2.utils.MessageUtils;

/**
 * @author Chun-Feng Liao (1.0)
 */
@ListeningTo("home/COMMAND")
public class DateExample extends MqttPerNode
{
    private Logger log = Logger.getLogger(DateExample.class);

    public void processMessage(String topic, String message)
    {
        // 2011_8_16
        // input 15_11_02 HH_mm_ss
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.AUGUST, 16, 15, 11, 02);
        Date date = calendar.getTime();
        String dateTimeString = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINESE).format(date);
        try
        {
            Date recoveredDate = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINESE).parse(dateTimeString);
            log.info(recoveredDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        getSender().send("home/CONTEXT", MessageUtils.jsonBuilder().add("subject", "temperature").add("value", "200").add("time", dateTimeString).toJson());
    }

    public static void main(String[] args)
    {
        NodeManager ctx = new MqttNodeManager();
        ctx.setNode(new DateExample());
        ctx.start();
    }
}
