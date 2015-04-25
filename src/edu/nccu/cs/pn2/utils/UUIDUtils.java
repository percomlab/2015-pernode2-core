package edu.nccu.cs.pn2.utils;

import java.util.UUID;

public class UUIDUtils
{

    public synchronized static String getUUID()
    {
        return UUID.randomUUID().toString();
    }

}
