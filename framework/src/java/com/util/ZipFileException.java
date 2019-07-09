package com.util;

public class ZipFileException extends RuntimeException
{
    public ZipFileException()
    {
        super();
    }

    public ZipFileException(final String message)
    {
        super(message);
    }

    public ZipFileException(final Throwable cause)
    {
        super(cause);
    }

    public ZipFileException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
