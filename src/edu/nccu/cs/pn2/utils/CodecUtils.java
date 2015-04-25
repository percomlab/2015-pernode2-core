package edu.nccu.cs.pn2.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class CodecUtils
{
    /**
     * 將輸入Big5碼字串轉成Unicode碼字串。
     * 
     * @param sInBig5String
     *            (String)輸入的Big5碼字串。
     * @return (String)Unicode碼字串 。
     */
    public static String toUnicode(String sInBig5String)
    {
        if (sInBig5String != null)
        {
            try
            {
                return new String(sInBig5String.getBytes("ISO8859_1"), "Big5");
            }
            catch (UnsupportedEncodingException e)
            {
                return sInBig5String;
            }
        } else
        {
            return null;
        }
    }

    /**
     * 將輸入Unicode碼字串轉成Big5碼字串。
     * 
     * @param sInUnicodeString
     *            (String)輸入的Unicode碼字串。
     * @return (String)Big5碼字串。
     */
    public static String toBig5(String sInUnicodeString)
    {
        if (sInUnicodeString != null)
        {
            try
            {
                return new String(sInUnicodeString.getBytes("MS950"), "ISO8859_1");
            }
            catch (UnsupportedEncodingException e)
            {
                return sInUnicodeString;
            }
        } else
        {
            return null;
        }
    }

    public static String stringToBase64Hex(String input)
    {
        return new String(Base64.encodeBase64(input.getBytes()));
    }

    public static String base64HexToString(String input)
    {
        try
        {
            //System.out.println(input);
            return new String(Base64.decodeBase64(input.getBytes()));
        }
        catch (Exception e)
        {
            throw new NotValidBase64HexException(e.getMessage());
        }

    }
}
