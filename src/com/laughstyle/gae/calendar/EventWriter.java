package com.laughstyle.gae.calendar;

import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.servlet.http.Cookie;

import com.fins.org.json.HTTP;
import com.google.gdata.client.*;
import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.client.calendar.CalendarService.Versions;
import com.google.gdata.client.http.GoogleGDataRequest.GoogleCookie;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.ExtensionProfile;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;
import com.google.gdata.data.extensions.Who;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.common.xml.XmlWriter;
import com.laughstyle.gae.data.LSConverter;

public class EventWriter {
	private CalendarService service;

	private URL feedURL;

	public EventWriter(String calendarName, String sessionToken)
			throws MalformedURLException
	{
		service = CalendarServiceFactory.create(calendarName, sessionToken);
		this.feedURL = new URL(String.format("http://www.google.com/calendar/feeds/%s/private/full", calendarName));
	}

	public CalendarEventEntry insert(CalendarEventEntry event)
	{
    	CalendarEventEntry newEvent = null;
    	
    	try
    	{
    		newEvent = service.insert(this.feedURL, event);
    	}
    	catch(com.google.gdata.util.ServiceException se)
    	{
    	}
    	catch(IOException ie)
    	{
    	}

    	return newEvent;
	}
	
	private URL parse302Exception(ServiceException e, URL targetURL)
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
	
	private CalendarEventEntry partialUpdateCore(URL url, CalendarEventEntry event)
		throws ServiceException
	{
		CalendarEventEntry updatedEntry = null;
		try
		{
			String fieldsToUpdate = "atom:title,atom:content,gd:where,gd:when";
			updatedEntry = service.patch(url, fieldsToUpdate, event);				
		}
		catch(IOException ie)
		{			
		}
		
		return updatedEntry;
	}
	
	public CalendarEventEntry partialUpdate(URL url, CalendarEventEntry event)
	{
		CalendarEventEntry updatedEntry = null;
		try
		{
			updatedEntry = partialUpdateCore(url, event);
		}
		catch(ServiceException se)
		{
			// TemporaryRedirectの無限ループになるので再帰による実装はしない
			URL tempRedirectURL = parse302Exception(se, url);
			if(null != tempRedirectURL)
			{
				try
				{
					updatedEntry = partialUpdateCore(tempRedirectURL, event);
				}
				catch (ServiceException e) 
				{
				}
			}
		}

		return updatedEntry;
	}
	
	public CalendarEventEntry update(URL url, CalendarEventEntry event)
	{
		CalendarEventEntry updatedEvent = null;
		try
		{	
			URL editUrl = url;
			service.getRequestFactory().setHeader("if-Match", event.getEtag());
			updatedEvent = (CalendarEventEntry)service.update(editUrl, event);	
		}
		catch(ServiceException se)
		{	
			System.out.println(se.toString());
		}
		catch(IOException ie)
		{			
			System.out.println(ie.toString());
		}
		return updatedEvent;
	}
	
	public CalendarEventEntry update(CalendarEventEntry event)
	{
		CalendarEventEntry updatedEvent = null;
		try
		{
			URL editUrl = new URL(event.getEditLink().getHref());
			service.getRequestFactory().setHeader("if-Match", event.getEtag());
			updatedEvent = (CalendarEventEntry)service.update(editUrl, event);	
		}
		catch(MalformedURLException me)
		{		
		}
		catch(ServiceException se)
		{	
		}
		catch(IOException ie)
		{			
		}
		return updatedEvent;
	}
	
	public boolean delete(String url,String etag)
	{
		boolean result = false;
		try
		{
			URL editUrl = new URL(url);
			service.getRequestFactory().setHeader("if-Match", etag);
			
			service.delete(editUrl);
			result = true;
		}
		catch(MalformedURLException me)
		{		
		}
		catch(ServiceException se)
		{
			System.out.println(se.toString());
		}
		catch(IOException ie)
		{			
		}
		return result;
		
	}
	
	public boolean delete(CalendarEventEntry event)
	{
		String url = event.getEditLink().getHref();	

		return delete(url,event.getEtag());
	}
}
