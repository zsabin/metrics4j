package org.kairosdb.metrics4j;


import org.kairosdb.metrics4j.util.Clock;
import org.kairosdb.metrics4j.util.SystemClock;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class ReportingQueue
{
	private final Clock clock;
	private final Queue<DataPoint> queue = new ConcurrentLinkedQueue<>();

	public ReportingQueue()
	{
		this.clock = new SystemClock();
	}

	ReportingQueue(Clock clock)
	{
		this.clock = clock;
	}

	public <T> void add(Metric<T> metric, T value)
	{
		//TODO aggregate data into buckets
		queue.add(new DataPoint<>(metric, clock.now(), value));
	}

	public DataPoint poll()
	{
		return queue.poll();
	}
}
