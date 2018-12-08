package org.kairosdb.metrics4j;

import java.util.Objects;

public class DataPoint<T>
{
	private final Metric metric;
	private final long timestamp;
	private final T value;

	public DataPoint(Metric<T> metric, long timestamp, T value)
	{
		this.metric = Objects.requireNonNull(metric, "metric is null");
		this.timestamp = timestamp;
		this.value = Objects.requireNonNull(value, "value is null");
	}

	public Metric getMetric()
	{
		return metric;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public T getValue()
	{
		return value;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DataPoint dataPoint = (DataPoint) o;
		return Objects.equals(metric, dataPoint.metric) &&
				timestamp == dataPoint.timestamp &&
				Objects.equals(value, dataPoint.value);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(metric, timestamp, value);
	}
}
