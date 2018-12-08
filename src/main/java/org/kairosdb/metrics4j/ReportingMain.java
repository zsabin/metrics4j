package org.kairosdb.metrics4j;

import java.util.List;

public class ReportingMain
{
	private List<ReportingClient> clients;

	//TODO how to initiate the reporting system?
	public void init()
	{
		for (ReportingClient client : clients) {
			ReportingQueue queue = new ReportingQueue();
			Reporter.getReporter().addListener(queue::add);
			//TODO create system to push data to clients on regular interval
		}
	}
}
