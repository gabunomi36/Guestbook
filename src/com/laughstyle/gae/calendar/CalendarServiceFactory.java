package com.laughstyle.gae.calendar;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.util.AuthenticationException;

public class CalendarServiceFactory {

	/**
	 * @param name
	 * @param token
	 * @return
	 */
	public static CalendarService create(String name, String token)
	{
		CalendarService service = new CalendarService(name);
		service.setAuthSubToken(token);
		
		return service;
	}
	
	/**
	 * @param name
	 * @param gmail
	 * @param passwd
	 * @return
	 * @throws AuthenticationException
	 */
	public static CalendarService create(String name, String gmail, String passwd)
				throws AuthenticationException
	{
		CalendarService service = new CalendarService(name);
    	service.setUserCredentials(gmail, passwd);
		return service;
	}
	
}
