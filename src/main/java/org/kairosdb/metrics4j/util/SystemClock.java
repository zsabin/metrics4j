package org.kairosdb.metrics4j.util;

public class SystemClock implements Clock
{
    @Override
    public long now()
    {
        return System.currentTimeMillis();
    }
}
