package org.kairosdb.metrics4j;

public interface MetricListener
{
    <T> void report(Metric<T> metric, T value);
}
