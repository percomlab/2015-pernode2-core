package edu.nccu.cs.pn2.utils;

public class ByteUtils
{
    /*
     * merge 2 byte to double
     */
    public static double mergeAsDouble(byte[] data, int first, int second)
    {
        double dataValue = data[first] << 8 | data[second] & 0xff;
        return dataValue;
    }

    public static String toString(final byte[] buf, final int lenght)
    {
        int limit = lenght;
        if (buf.length < lenght)
        {
            limit = buf.length;
        }

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < limit; i++)
        {
            buffer.append(Integer.toHexString(buf[i] & 0xff)).append(' ');
        }
        return buffer.toString();
    }
}
