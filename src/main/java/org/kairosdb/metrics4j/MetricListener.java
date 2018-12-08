package org.kairosdb.metrics4j;

/**
 * An interface used to listen to reported metric data. Instances of this interface may be
 * registered with a {@link Reporter} to start listening to this data.
 */
public interface MetricListener
{
    <T> void report(Metric<T> metric, T value);
}
