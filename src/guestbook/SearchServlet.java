package guestbook;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.io.*;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;
import com.laughstyle.gae.calendar.CalendarEvent;
import com.laughstyle.gae.calendar.EventReader;
import com.laughstyle.gae.data.DateTimeParser;

public class SearchServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		doPost(req, resp);
	}
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {

		String sessionToken = req.getParameter("token");
		String startDay = req.getParameter("startday");
		String endDay = req.getParameter("endday");	
		String calendarName = req.getParameter("calendar");
		
		EventReader er = new EventReader(calendarName, sessionToken);
		try {
			er.setMinimumStartTime(DateTimeParser.parse(startDay + " 00:00:00"));
			er.setMaximumStartTime(DateTimeParser.parse(endDay + " 23:59:59"));
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		er.fetch();
		List<CalendarEvent> list = parseCalendarEvents(er.getEvents());
		
		String json = JSON.encode(list);

		json = String.format("{data:%s,recordType:'object'}", json);
		System.out.println(json);
		
		resp.setHeader("Content-Disposition", "filename=search.json");
		resp.setContentType("text/josn;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		 
		out.print(json);
		out.close();

	}

	private List<CalendarEvent> parseCalendarEvents(List<CalendarEventEntry>events)
	{
		ArrayList<CalendarEvent> list = new ArrayList<CalendarEvent>();
		
		for(CalendarEventEntry event : events)
    	{
    //		String eventTime = "";
			String[] startDatetime = null;
			String[] endDatetime = null;
    		List<When> times = event.getTimes();
    		if(!times.isEmpty())
    		{
    			When when = times.get(0);
       			startDatetime = DateTimeParser.split( when.getStartTime().toUiString());
       			endDatetime = DateTimeParser.split( when.getEndTime().toUiString());
       		 //   			eventTime = String.format("%s〜%s", when.getStartTime().toUiString(), when.getEndTime().toUiString());
    		}
    		
    		String eventLocation = "";
    		List<Where> locations= event.getLocations();
    		if(!locations.isEmpty())
    		{
    			Where where = locations.get(0);
    			eventLocation = where.getValueString();
    		}
    		
    		String ext = "";
			List<ExtendedProperty>lp = event.getExtendedProperty();
			for(ExtendedProperty p : lp)
			{
				ext += String.format("key:%s,value:%s; ", p.getName(), p.getValue());
			}

    		try
    		{
    			URL editLink = null;
    			if(event.getEditLink() != null)
    			{
    				editLink = new URL(event.getEditLink().getHref());
    			}
    			
    			list.add(new CalendarEvent(editLink, event.getTitle().getPlainText(),event.getTextContent().getContent().getPlainText(),startDatetime[0], startDatetime[1], endDatetime[0], endDatetime[1],eventLocation,ext, event.getEtag()));   	
    		}
    		catch(Exception ex)
    		{
    			System.out.println(ex.toString());
    		}
    		
    	}
		
		return list;
	}
}
