package org.kairosdb.metrics4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kairosdb.metrics4j.annotation.Key;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class StatsInvocationHandlerTest
{
    private StatsInvocationHandler invocationHandler;
    private ExpectedStats expectedStats = new ExpectedStats();

    @BeforeEach
    public void setup()
    {
        invocationHandler = new StatsInvocationHandler();
    }

    private interface TestStats
    {
        Metric noTags();

        Metric withTags(@Key("tag1") String tag1, @Key("tag2") String tag2);
    }

    private class ExpectedStats implements TestStats
    {
        public Metric noTags()
        {
            return new Metric("noTags", new HashMap<>());
        }

        public Metric withTags(@Key("tag1") String tag1, @Key("tag2") String tag2)
        {
            Map<String, Object> tags = new HashMap<>();
            tags.put("tag1", tag1);
            tags.put("tag2", tag2);
            return new Metric("withTags", tags);
        }
    }

    @Test
    public void test_invoke_noTags()
            throws NoSuchMethodException
    {
        Method method = TestStats.class.getMethod("noTags");

        Metric metric = (Metric) invocationHandler.invoke(null, method, null);

        assertEquals(expectedStats.noTags(), metric);
    }

    @Test
    public void test_invoke_withTags()
            throws NoSuchMethodException
    {
        Method method = TestStats.class.getMethod("withTags", String.class, String.class);

        Metric metric = (Metric) invocationHandler.invoke(null, method, new String[]{"one", "two"});

        assertEquals(expectedStats.withTags("one", "two"), metric);
    }

    @Test
    public void test_invoke_multipleInvocationsShouldReturnSameObject()
            throws NoSuchMethodException
    {
        Method method = TestStats.class.getMethod("noTags");

        Metric metric1 = (Metric) invocationHandler.invoke(null, method, null);
        Metric metric2 = (Metric) invocationHandler.invoke(null, method, null);

        assertSame(metric1, metric2);
    }
}