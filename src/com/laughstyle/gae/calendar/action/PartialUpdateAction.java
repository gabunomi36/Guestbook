package com.laughstyle.gae.calendar.action;

import java.io.IOException;
import java.net.URL;

import org.datanucleus.query.expression.ParameterExpression;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.util.ServiceException;

public class PartialUpdateAction extends BasicAction {

	private String updateFields = null;
	
	public PartialUpdateAction(CalendarService service) {
		super(service);
	}

	public PartialUpdateAction(String calendarName, String sessionToken) {
		super(calendarName, sessionToken);
	}

	private CalendarEventEntry partialUpdateCore(URL url, CalendarEventEntry event) throws ServiceException
	{
		CalendarEventEntry updatedEntry = null;
		try
		{
			updatedEntry = service.patch(url, getUpdateFields(), event);				
		}
		catch(IOException ie)
		{			
		}
		
		return updatedEntry;
	}

	public CalendarEventEntry doAction(CalendarEventEntry event) {
		CalendarEventEntry updatedEntry = null;
		URL url = this.getFeedURL();
		
		if(null == url)
		{
			url = getSafeEditURL(event);
			if(null == url)
			{
				throw new NullPointerException();
			}
		}
		
		try
		{
			updatedEntry = partialUpdateCore(url, event);
		}
		catch(ServiceException e)
		{
			// TemporaryRedirectの無限ループになるので再帰による実装はしない
			URL tempRedirectURL = ServiceException2TempraryRedirectURL(e, url);
			if(null != tempRedirectURL)
			{
				try
				{
					updatedEntry = partialUpdateCore(tempRedirectURL, event);
				}
				catch (ServiceException se) 
				{
				}
			}
		}

		return updatedEntry;
	}

	public void setUpdateFields(String updateFields) {
		this.updateFields = updateFields;
	}

	public String getUpdateFields() {
		if(null == this.updateFields)
			this.updateFields = "atom:title,atom:content,gd:where,gd:when";

		return updateFields;
	}
}
