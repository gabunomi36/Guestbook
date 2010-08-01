package com.laughstyle.gae.calendar;

import java.net.*;
import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.data.DateTime;

public class CalendarQueryBuilder {
	
	private URL feedURL = null;
	
	public CalendarQueryBuilder(URL feedURL)
	{
		this.feedURL = feedURL;
	}
	
	public CalendarQuery getCalendarQuery(DateTime minimumStart, DateTime maxmumStart)
	{
		CalendarQuery query = new CalendarQuery(feedURL);
		query.setMinimumStartTime(minimumStart);
		query.setMaximumStartTime(maxmumStart);
		return query;
	}

}
