package guestbook;

import java.net.URL;
import java.util.*;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;
import com.laughstyle.gae.calendar.CalendarEvent;

public class CalendarController 
{
	// 認証されたtoken
	private String sessionToken;
	
	// カレンダーイベントのリスト
	private List<CalendarEvent> events;
	
	public CalendarController(String sessionToken)
	{
		// Google Calendarの認証に使用したtokenを保持する
		this.sessionToken = sessionToken;
	}
	
	public List<CalendarEvent> getEvents()
	{
		ArrayList<CalendarEvent> list = new ArrayList<CalendarEvent>();

		try{
	        CalendarService myService = new CalendarService("GAE/J Demo");
	        myService.setAuthSubToken(sessionToken);
           	URL feedUrl = new URL("http://www.google.com/calendar/feeds/hiroki.touma@laughstyle-office.com/private/full");

			CalendarQuery myQuery = new CalendarQuery(feedUrl);
			myQuery.setMinimumStartTime(DateTime.parseDateTime("2009-11-10T16:00:00"));
			myQuery.setMaximumStartTime(DateTime.parseDateTime("2010-11-11T16:00:00"));

         	CalendarEventFeed resultFeed = myService.query(myQuery, CalendarEventFeed.class);

        	for(CalendarEventEntry event : resultFeed.getEntries())
        	{
        		String eventTime = "";
        		List<When> times = event.getTimes();
        		if(!times.isEmpty())
        		{
        			When when = times.get(0);
        			eventTime = String.format("%s～%s", when.getStartTime().toUiString(), when.getEndTime().toUiString());
        		}
        		
        		String eventLocation = "";
        		List<Where> locations= event.getLocations();
        		if(!locations.isEmpty())
        		{
        			Where where = locations.get(0);
        			eventLocation = where.getValueString();
        		}
        		
 //       		list.add(new CalendarEvent(event.getTitle().getPlainText(),event.getTextContent().getContent().getPlainText(),eventTime,eventLocation));
        	}
		}
		catch(Exception ex)
		{
			
		}
		
		events = list;
		return list;
	}
}
