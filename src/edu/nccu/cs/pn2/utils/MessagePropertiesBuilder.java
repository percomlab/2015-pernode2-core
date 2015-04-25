package edu.nccu.cs.pn2.utils;

import java.util.HashMap;
import java.util.Map;

public class MessagePropertiesBuilder
{
    private Map<String, String> props = new HashMap<String, String>();

    public MessagePropertiesBuilder add(String property, String value)
    {
        props.put(property, value);
        return this;
    }

    public Map<String, String> toMap()
    {
        return props;
    }
    
    public void reset()
    {
        props.clear();
    }
}
