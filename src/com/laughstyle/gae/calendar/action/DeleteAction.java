package com.laughstyle.gae.calendar.action;

import java.io.IOException;
import java.net.URL;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.util.ServiceException;

public class DeleteAction extends BasicAction {

	public DeleteAction(CalendarService service) {
		super(service);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public DeleteAction(String calendarName, String sessionToken) {
		super(calendarName, sessionToken);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	private CalendarEventEntry updateCore(URL url, CalendarEventEntry event) throws ServiceException
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
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
