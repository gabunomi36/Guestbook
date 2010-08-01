package guestbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.security.action.GetLongAction;

import com.fins.gt.server.*;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.util.ServiceException;
import com.laughstyle.gae.calendar.CalendarEvent;
import com.laughstyle.gae.calendar.CalendarServiceFactory;
import com.laughstyle.gae.calendar.EventFactory;
import com.laughstyle.gae.calendar.EventWriter;
import com.laughstyle.gae.calendar.action.PartialUpdateAction;
import com.laughstyle.gae.data.DateTimeParser;

import net.arnx.jsonic.JSON;

public class WriterServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		doPost(req, resp);
	}
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {

		boolean result = true;
		String exception = null;
		
		Logger logger = Logger.getLogger(this.getClass().getName());
		logger.info("start");

		GridServerHandler handler = new GridServerHandler(req,resp);
		
		String act = handler.getAction();
		String sessionToken = req.getParameter("token");
		String calendarName = req.getParameter("calendar");

		if(act.equals("save"))
		{
			List<CalendarEvent> lst = handler.getUpdatedRecords(CalendarEvent.class);

			for(CalendarEvent event:lst)
			{
				try
				{
					DateTime sd = DateTimeParser.parse(event.getStartDate(), event.getStartTime());
					DateTime ed = DateTimeParser.parse(event.getEndDate(), event.getEndTime());
					CalendarEventEntry changedEvent = EventFactory.create(event.getTitle(), event.getContent(), event.getWhere(), sd, ed, null, event.getEtag());
					URL feedURL = event.getUrl();
					String serviceName = calendarName/*"hiroki.touma@laughstyle-office.com"*/;
					
					CalendarService service = CalendarServiceFactory.create(serviceName, sessionToken);
					PartialUpdateAction action = new PartialUpdateAction(service);

					action.setFeedURL(feedURL);
					if(null == action.doAction(changedEvent))
					{
						throw new Exception("partialUpdateActionException");
					}
				}
				catch(Exception ex)
				{
					logger.info("partial update exception:" + ex.toString());
					System.out.println(ex.toString());
					result = false;
					exception = ex.toString();
					break;
				}
			}

			if(result){
				lst = handler.getInsertedRecords(CalendarEvent.class);
				for(CalendarEvent event:lst)
				{
					DateTime sd;
					try {
						sd = DateTimeParser.parse(event.getStartDate(), event.getStartTime());
						DateTime ed = DateTimeParser.parse(event.getEndDate(), event.getEndTime());
						CalendarEventEntry changedEvent = EventFactory.create(event.getTitle(), event.getContent(), event.getWhere(), sd, ed,null, event.getEtag());
						EventWriter writer = new EventWriter(calendarName/*"hiroki.touma@laughstyle-office.com"*/, sessionToken);
	//					writer.insert(changedEvent);
	
						if(null == writer.insert(changedEvent))
						{
							throw new Exception("partialUpdateActionException");
						}
					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
						exception = e.toString();
						result = false;
						break;
					}
				}
			}
			
		}
		
		handler.setSuccess(result);
		if(!result)
		{
			handler.setException("例外でとるがな");
		}

		PrintWriter out = resp.getWriter();
		out.print(handler.getSaveResponseText());
		out.flush();
		out.close();
	}
}
