package com.laughstyle.gae.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.net.*;
import sun.awt.HorizBagLayout;

import com.google.appengine.api.datastore.Link;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;
import com.laughstyle.gae.data.LSConverter;

public class EventFactory {
	public static CalendarEventEntry create(String title, String content, String location, DateTime startTime, DateTime endTime)
	{
		CalendarEventEntry event = new CalendarEventEntry();
		event.setTitle(LSConverter.String2TextConstruct(title));
    	When w = new When();
		w.setStartTime(startTime);
		w.setEndTime(endTime);
		
		event.addTime(w);
		
		Where loc = new Where();
		loc.setValueString(location);
		event.addLocation(loc);
    	
		event.setContent(LSConverter.String2TextConstruct(content));
		
		return event;
	}
	public static CalendarEventEntry create(String title, String content, String location, DateTime startTime, DateTime endTime,URL url, String etag)
	{
		CalendarEventEntry event = new CalendarEventEntry();
		event.setTitle(LSConverter.String2TextConstruct(title));
    	When w = new When();
		w.setStartTime(startTime);
		w.setEndTime(endTime);
		
		event.addTime(w);
		
		Where loc = new Where();
		loc.setValueString(location);
		event.addLocation(loc);
    	
		event.setContent(LSConverter.String2TextConstruct(content));
		
		event.setEtag(etag);
		return event;
	}
}
