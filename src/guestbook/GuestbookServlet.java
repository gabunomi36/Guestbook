package guestbook;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.*;
import javax.servlet.*;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;
import com.laughstyle.gae.calendar.*;
import com.laughstyle.gae.data.ExtendedPropertyFactory;

import net.arnx.jsonic.*;

@SuppressWarnings("serial")
public class GuestbookServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());  
    
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		try{
			RequestDispatcher rd = req.getRequestDispatcher("/list2.jsp");
			rd.forward(req, resp);
		}
		catch(Exception ex)
		{
		}
	}
}
