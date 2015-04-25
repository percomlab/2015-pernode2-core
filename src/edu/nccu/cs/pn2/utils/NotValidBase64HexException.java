package edu.nccu.cs.pn2.utils;

@SuppressWarnings("serial")
public class NotValidBase64HexException extends RuntimeException
{

    public NotValidBase64HexException()
    {
    }

    public NotValidBase64HexException(String message)
    {
        super(message);
    }

}
