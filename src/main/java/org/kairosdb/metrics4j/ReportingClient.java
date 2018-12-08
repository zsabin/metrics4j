package org.kairosdb.metrics4j;

import java.util.Set;

public interface ReportingClient
{
	void report(Set<DataPoint> data);
}
