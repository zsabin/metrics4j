package org.kairosdb.metrics4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Reporter
{
	private static final Reporter instance = new Reporter();

	public static Reporter getReporter()
	{
		return instance;
	}

	private final CopyOnWriteArraySet<MetricListener> listeners = new CopyOnWriteArraySet<>();

	public Set<MetricListener> getListeners()
	{
		return new HashSet<>(listeners);
	}

	public void addListener(MetricListener listener)
	{
		listeners.add(listener);
	}

	public void removeListener(MetricListener listener)
	{
		listeners.remove(listener);
	}

	public <T> void report(Metric<T> metric, T value)
	{
		for (MetricListener handler : listeners) {
			handler.report(metric, value);
		}
	}
}
