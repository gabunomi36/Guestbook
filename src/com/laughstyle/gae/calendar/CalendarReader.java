package com.laughstyle.gae.calendar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.util.ServiceException;

public class CalendarReader {
	private CalendarService service = null;
	private URL feedURL = null;

	public CalendarReader(String sessionToken) throws MalformedURLException
	{
		this("default", sessionToken);
	}
	
	public CalendarReader(String calendarName, String sessionToken)
				throws MalformedURLException
	{
		service = CalendarServiceFactory.create(calendarName, sessionToken);
		this.feedURL = new URL("http://www.google.com/calendar/feeds/default/allcalendars/full");
	}

	public List<String[]> getCalendarNames()
	{
		ArrayList<String[]> result = new ArrayList<String[]>();
		
		CalendarFeed resultFeed;
		try {
			resultFeed = service.getFeed(this.feedURL, CalendarFeed.class);
			for(CalendarEntry calendar: resultFeed.getEntries())
			{
				String id = calendar.getId();
				String[] ids = id.split("/");
				if(0 < ids.length)
				{
					id = ids[ids.length-1];
				}
				result.add(new String[]{calendar.getTitle().getPlainText(),URLDecoder.decode(id, "UTF-8")});
			}			
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return result;
	}
}
