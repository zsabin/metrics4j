package org.kairosdb.metrics4j;

import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class Metric<T>
{
	private final String name;
	private final Map<String, Object> tags;

	public Metric(String name, Map<String, Object> tags)
	{
		this.name = Objects.requireNonNull(name, "name is null");
		this.tags = Objects.requireNonNull(tags, "tags is null");
	}

	public String getName()
	{
		return name;
	}

	public Map<String, Object> getTags()
	{
		return tags;
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
		Metric metric = (Metric) o;
		return Objects.equals(name, metric.name) &&
				Objects.equals(tags, metric.tags);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name, tags);
	}
}
