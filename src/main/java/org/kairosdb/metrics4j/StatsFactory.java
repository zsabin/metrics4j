package org.kairosdb.metrics4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StatsFactory provides a mechanism for generating {@link Metric} objects in a consistent
 * manner based on constraints specified in user-defined interfaces.
 */
public class StatsFactory
{
	private static Map<Class, StatsInvocationHandler> s_invocationMap = new ConcurrentHashMap<>();

	public static <T> T getStats(Class<T> tClass)
	{
		// Metric objects can be instantiated directly. This method simply prevents the need
		// for boilerplate code to generate these metrics. We could achieve the same result
		// through source code generation rather than proxy objects.

		// In the future this class could also be used to register information about the
		// metric such as the strategy to be used to aggregate its data into buckets

		InvocationHandler handler = s_invocationMap.computeIfAbsent(tClass, (klass) -> new StatsInvocationHandler());

		//not sure if we should cache proxy instances or create new ones each time.
		Object proxyInstance = Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass},
				handler);

		return (T) proxyInstance;
	}
}
