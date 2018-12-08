package org.kairosdb.metrics4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

// This class provides a layer of abstraction between the creation of metric data and
// the aggregation of that data into buckets. It produces a stateless stream of data
// that other classes can listen in on in. Using this interface, rather than stateful
// stats objects, like Counters, prevents unnecessary coupling between the creation of
// metric data and the reporting of that data to various clients.

// This interface also provides additional flexibility over stateful objects. We can
// for example, report metric data at different levels, similar to a logger, or record
// information about the reporting class. However, we need not abandon the stats object
// model entirely. If we feel that model is simpler to use we could create stateless
// stats objects that simply wrap a Reporter.
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
