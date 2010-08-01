package guestbook;

import java.io.*;
import java.util.*;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.*;
import javax.servlet.*;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;


@SuppressWarnings("serial")
public class SignOnServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		String fowarding = "/guestbook";
		RequestDispatcher rd = req.getRequestDispatcher(fowarding);
		
		try
		{
			rd.forward(req, resp);
		} 
		catch (ServletException e)
		{
			e.printStackTrace();
		}
	}
}
