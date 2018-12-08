package org.kairosdb.metrics4j;

import org.junit.jupiter.api.Test;
import org.kairosdb.metrics4j.annotation.Key;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class StatsFactoryTest
{
	private interface TestStats
	{
		Metric test(@Key("foo") String foo);
	}

	@Test
	public void test_getStats()
	{
		TestStats testStats = StatsFactory.getStats(TestStats.class);
		assertNotNull(testStats);
	}
}