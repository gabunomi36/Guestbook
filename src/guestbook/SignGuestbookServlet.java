package guestbook;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import guestbook.Greeting;
import guestbook.PMF;
import com.google.gdata.client.*;
import com.google.gdata.client.calendar.*;
import com.google.gdata.data.*;
import com.google.gdata.data.acl.*;
import com.google.gdata.data.calendar.*;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;
import java.net.URL;

@SuppressWarnings("serial")
public class SignGuestbookServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        String content = req.getParameter("content");
        Date dt = new Date();
        Greeting greeting = new Greeting(user, content, dt);
        
        CalendarService myService = new CalendarService("hiroki.touma@laughstyle-office.com");
        
        try{
        	myService.setUserCredentials("hiroki.touma@gmail.com", "tiger789");
        	
//           	URL feedUrl = new URL("http://www.google.com/calendar/feeds/default/allcalendars/full");
           	URL feedUrl = new URL("http://www.google.com/calendar/feeds/tomokotouma@gmail.com/private/full");
                   	        	
        	CalendarQuery myQuery = new CalendarQuery(feedUrl);
    	   myQuery.setMinimumStartTime(DateTime
                   .parseDateTime("2009-11-10T16:00:00"));
           myQuery.setMaximumStartTime(DateTime
                   .parseDateTime("2010-11-11T16:00:00"));

        	CalendarEventFeed resultFeed = myService.query(myQuery, CalendarEventFeed.class);

//        	CalendarFeed resultFeed = myService.getFeed(feedUrl,CalendarFeed.class);
        	System.out.println("Your calendars:" + resultFeed.getEntries().size());
        	System.out.println();
        	
        	for (int i = 0; i < resultFeed.getEntries().size(); i++) {
          	  CalendarEventEntry entry = resultFeed.getEntries().get(i);
//         	  CalendarEntry entry = resultFeed.getEntries().get(i);
        	  System.out.println("\t" + entry.getTitle().getPlainText());
        	}

        }
        catch(Exception e)
        {
        	System.out.println("reigai");
        }
        PersistenceManager pm = PMF.get().getPersistenceManager();
        
        try{
        	pm.makePersistent(greeting);
        } finally{
        	pm.close();
        }
        resp.sendRedirect("guestbook.jsp");
    }

}
