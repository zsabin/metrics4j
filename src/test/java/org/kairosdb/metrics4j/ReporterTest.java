package org.kairosdb.metrics4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ReporterTest
{
	@Mock
	private Metric metric;

	private Reporter reporter;

	@BeforeEach
	private void setup()
	{
		this.reporter = new Reporter();
	}

	@Test
	public void test_getListeners_noListeners()
	{
		assertTrue(reporter.getListeners().isEmpty());
	}

	@Test
	public void test_addListener_singleListener()
	{
		MetricListener listener = mock(MetricListener.class);

		reporter.addListener(listener);

		assertEquals(Collections.singleton(listener), reporter.getListeners());
	}

	@Test
	public void test_addListener_multipleListeners()
	{
		MetricListener listener1 = mock(MetricListener.class);
		MetricListener listener2 = mock(MetricListener.class);

		reporter.addListener(listener1);
		reporter.addListener(listener2);

		assertEquals(new HashSet<>(Arrays.asList(listener1, listener2)), reporter.getListeners());
	}

	@Test
	public void test_addListener_duplicateListeners()
	{
		MetricListener listener = mock(MetricListener.class);

		reporter.addListener(listener);
		reporter.addListener(listener);

		assertEquals(Collections.singleton(listener), reporter.getListeners());
	}

	@Test
	public void test_removeListener_listenerNotRegistered_shouldNotThrowException()
	{
		reporter.removeListener(mock(MetricListener.class));
	}

	@Test
	public void test_removeListener()
	{
		MetricListener listener = mock(MetricListener.class);

		reporter.addListener(listener);
		reporter.removeListener(listener);

		assertTrue(reporter.getListeners().isEmpty());
	}

	@Test
	public void test_report_noListeners_shouldNotThrowException()
	{
		reporter.report(metric, 1);
	}

	@Test
	public void test_report_singleListener()
	{
		MetricListener listener = mock(MetricListener.class);
		reporter.addListener(listener);

		reporter.report(metric, 1);

		verify(listener).report(metric, 1);
	}

	@Test
	public void test_report_multipleListeners()
	{
		MetricListener listener1 = mock(MetricListener.class);
		MetricListener listener2 = mock(MetricListener.class);
		reporter.addListener(listener1);
		reporter.addListener(listener2);

		reporter.report(metric, 1);

		verify(listener1).report(metric, 1);
		verify(listener2).report(metric, 1);
	}

	@Test
	public void test_report_shouldNotReportToListenerAfterRemoval()
	{
		MetricListener listener = mock(MetricListener.class);
		reporter.addListener(listener);
		reporter.removeListener(listener);

		reporter.report(metric, 1);

		verify(listener, times(0)).report(metric, 1);
	}
}