package com.laughstyle.gae.calendar;

import java.net.URL;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.ColorProperty;
import com.google.gdata.data.calendar.HiddenProperty;
import com.google.gdata.data.calendar.TimeZoneProperty;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;

public class CalendarWriter {

	private CalendarService service;
		
	public CalendarWriter(String token)
	{
		service = new CalendarService("com.laughstyle");
		service.setAuthSubToken(token);		
	}
	
	public void writeCalendar()
	{
    	// Create the calendar
    	CalendarEntry calendar = new CalendarEntry();
    	calendar.setTitle(new PlainTextConstruct("kumakuma"));
    	calendar.setSummary(new PlainTextConstruct("‘æ‚R‰ñŒF‚Ì‚ ‚Â‚Ü‚è"));
    	calendar.setTimeZone(new TimeZoneProperty("Asia/Tokyo"));
    	calendar.setHidden(HiddenProperty.FALSE);
    	calendar.setColor(new ColorProperty("#2952A3"));
    	calendar.addLocation(new Where("","","Oakland"));

    	// Insert the calendar
    	try
    	{
    		URL postUrl = new URL("");
//        	CalendarEntry returnedCalendar = service.insert(postUrl, calendar);
        	
        	CalendarEventEntry myEntry = new CalendarEventEntry();
        	myEntry.setTitle(new PlainTextConstruct("‚½‚¢‚Æ‚é"));
        	When w = new When();
    		w.setStartTime(DateTime.parseDateTime("2010-06-09T19:00:00"));
    		w.setStartTime(DateTime.parseDateTime("2010-06-09T19:30:00"));
    		myEntry.addTime(w);
    		
    		Where loc = new Where();
    		loc.setValueString("’ƒƒOƒ}‚Ì‰Æ");
    		myEntry.addLocation(loc);
        	
        	myEntry.setContent(new PlainTextConstruct("‘æ‚R‰ñŒF‰ï‹c"));
//           	myEntry.setQuickAdd(true);

        	// Send the request and receive the response:
        	CalendarEventEntry insertedEntry = service.insert(postUrl, myEntry);
    	}
    	catch(Exception e)
    	{
    	
    	}
    	
   	}
}
