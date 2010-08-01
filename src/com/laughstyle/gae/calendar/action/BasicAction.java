package com.laughstyle.gae.calendar.action;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.Link;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.util.ServiceException;
import com.laughstyle.gae.calendar.CalendarServiceFactory;

public abstract class BasicAction {

	protected CalendarService service = null;
	protected URL feedURL = null;

	public BasicAction(CalendarService service)
	{
		this.service = service;
	}

	public BasicAction(String calendarName, String sessionToken)
	{
		this(CalendarServiceFactory.create(calendarName, sessionToken));
	}
	
	public void setFeedURL(URL feedURL) {
		this.feedURL = feedURL;
	}

	public URL getFeedURL() {
		return feedURL;
	}
	
	public abstract CalendarEventEntry doAction(CalendarEventEntry event);
	
	protected URL getSafeEditURL(CalendarEventEntry event)
	{
		Link editLink = event.getEditLink();
		String href = editLink.getHref();

		URL editURL = null;
		try {
			editURL = new URL(href);
		} catch (MalformedURLException e) {
		}
		return editURL;
	}
	
	protected URL ServiceException2TempraryRedirectURL(ServiceException e, URL targetURL)
	{
		URL tempRedirectURL = null;
		
		if(e.getHttpErrorCodeOverride() == 302)
		{
			try 
			{
				URL location = new URL(e.getHttpHeader("location").get(0));
				String urlWithGsessionid = String.format("%s?%s", targetURL.toString(), location.getQuery());

				tempRedirectURL = new URL(urlWithGsessionid);
			}
			catch (MalformedURLException ex)
			{
			}			
		}

		return tempRedirectURL;
	}

	public static URL createFeedURL(String calendarName)
	{
		URL url = null;
		
		try {
			url = new URL(String.format("http://www.google.com/calendar/feeds/%s/private/full", calendarName));
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return url;
	}
}
