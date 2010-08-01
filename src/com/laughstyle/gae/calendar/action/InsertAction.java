package com.laughstyle.gae.calendar.action;

import java.io.IOException;
import java.net.URL;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.util.ServiceException;

public class InsertAction extends BasicAction {

	public InsertAction(CalendarService service) {
		super(service);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public InsertAction(String calendarName, String sessionToken) {
		super(calendarName, sessionToken);
	}

	private CalendarEventEntry insertCore(URL url, CalendarEventEntry event) throws ServiceException
	{
		CalendarEventEntry newEvent = null;
    	try
    	{
    		newEvent = service.insert(url, event);
    	}
    	catch(IOException ie)
    	{
    	}

    	return newEvent;
	}
	
	@Override
	public CalendarEventEntry doAction(CalendarEventEntry event) {
		CalendarEventEntry newEvent = null;
		URL url = this.getFeedURL();
	
		try
		{
			newEvent = insertCore(url, event);
		}
		catch(ServiceException e)
		{
			// TemporaryRedirectの無限ループになるので再帰による実装はしない
			URL tempRedirectURL = ServiceException2TempraryRedirectURL(e, url);
			if(null != tempRedirectURL)
			{
				try
				{
					newEvent = insertCore(tempRedirectURL, event);
				}
				catch (ServiceException se) 
				{
				}
			}
		}
		return newEvent;
	}

}
