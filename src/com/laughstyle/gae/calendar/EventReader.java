package com.laughstyle.gae.calendar;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.*;

/**
 * @author touma
 *
 */
public class EventReader {
	private List<CalendarEventEntry> events = null;
	private CalendarService service = null;
	private URL feedURL = null;
	private CalendarQuery query = null;

	public EventReader(String sessionToken) throws MalformedURLException
	{
		this("default", sessionToken);
	}
	
	public EventReader(String calendarName, String sessionToken)
				throws MalformedURLException
	{
		service = CalendarServiceFactory.create(calendarName, sessionToken);
//		this.feedURL = new URL(String.format("http://www.google.com/calendar/feeds/%s/private/full", calendarName));
		this.feedURL = new URL(String.format("http://www.google.com/calendar/feeds/%s/private/full", calendarName));

		CalendarQueryBuilder builder = new CalendarQueryBuilder(feedURL);		
		this.query = builder.getCalendarQuery(null, null);
	}
	
	public void fetch()
	{		
		ArrayList<CalendarEventEntry> eventList = new ArrayList<CalendarEventEntry>();
		try{
			query.setMaxResults(100);
         	CalendarEventFeed resultFeed = service.query(query, CalendarEventFeed.class);
         	eventList.addAll(resultFeed.getEntries());
		}
		catch(Exception ex)
		{
			eventList.clear();
		}
		this.events = eventList;
	}
	
	public CalendarQuery getQuery()
	{
		return this.query;
	}
	
	public List<CalendarEventEntry> getEvents() {
		return events;
	}

	public void setMinimumStartTime(DateTime arg0) {
		query.setMinimumStartTime(arg0);
	}

	public void setMaximumStartTime(DateTime arg0) {
		query.setMaximumStartTime(arg0);
	}

	public void setMaxResults(int maxResults) {
		query.setMaxResults(maxResults);
	}
}
