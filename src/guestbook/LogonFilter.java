package guestbook;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import java.net.*;
import com.laughstyle.gae.*;
import com.laughstyle.gae.AuthSubURLFactory;
public class LogonFilter implements Filter {

	@Override
	public void destroy() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		String query = request.getQueryString();
		if(null == query)
		{
			String nextUrl = request.getRequestURL().toString();
			
			String domain = request.getParameter("domainName");
			if(null == domain)
				domain = "default";
				
			
//			String domain = "laughstyle-office.com";
			URL url = AuthSubURLFactory.getCalendarURL(domain, nextUrl, false, true);
			req.setAttribute("logonurl", url);

			response.sendRedirect(url.toString());
			return;
		}
		else
		{
			String token = SessionTokenFactory.getSessionToken(query);
			req.setAttribute("token", token);
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {


	}
}
