package org.kairosdb.metrics4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kairosdb.metrics4j.util.Clock;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ReportingQueueTest
{
	@Mock
	private Clock clock;

	private ReportingQueue queue;

	@BeforeEach
	public void setup()
	{
		initMocks(this);
		this.queue = new ReportingQueue(clock);
	}

	@Test
	public void test_poll_isEmpty()
	{
		assertNull(queue.poll());
	}

	@Test
	public void test_poll()
	{
		Metric metric = mock(Metric.class);
		long timestamp = System.currentTimeMillis();
		when(clock.now()).thenReturn(timestamp);

		queue.add(metric, 1);
		DataPoint dataPoint = queue.poll();

		assertEquals(new DataPoint(metric, timestamp, 1), dataPoint);
	}
}