package org.kairosdb.metrics4j;

import org.kairosdb.metrics4j.annotation.Key;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatsInvocationHandler implements InvocationHandler
{
	private final Map<ArgKey, Object> metricsMap = new ConcurrentHashMap<>();

	public Object invoke(Object proxy, Method method, Object[] args)
	{
		ArgKey key = new ArgKey(method, args);

		return metricsMap.computeIfAbsent(key, this::getMetric);
	}

	private Metric getMetric(ArgKey argKey)
	{
		Method method = argKey.getMethod();
		Parameter[] params = method.getParameters();

		Map<String, Object> tags = new HashMap<>();

		for (int i = 0; i < params.length; i++) {
			Parameter param = params[i];
			Key paramKey = param.getAnnotation(Key.class);
			tags.put(paramKey.value(), argKey.getArgs()[i]);
		}

		return new Metric(method.getName(), tags);
	}
}
