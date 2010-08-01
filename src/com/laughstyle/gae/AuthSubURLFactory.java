package com.laughstyle.gae;

import java.net.*;

import com.google.gdata.client.http.AuthSubUtil;

public class AuthSubURLFactory {
	/**
	 * @param domain
	 * @param nextUrl
	 * @param scope
	 * @param sequre
	 * @param session
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL getURL(String domain, String nextUrl, String scope, boolean sequre, boolean session)
					throws MalformedURLException
	{
		return new URL(AuthSubUtil.getRequestUrl(domain, nextUrl, scope, false, true));
	}
	
	/**
	 * @param nextUrl
	 * @param scope
	 * @param sequre
	 * @param session
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL getURL(String nextUrl, String scope, boolean sequre, boolean session)
					throws MalformedURLException
	{
		String domain = "default";
		return getURL(domain, nextUrl, scope,sequre, session);
	}
	
	/**
	 * @param domain
	 * @param nextUrl
	 * @param sequre
	 * @param session
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL getCalendarURL(String domain, String nextUrl, boolean sequre, boolean session)
					throws MalformedURLException
	{
		String scope = "http://www.google.com/calendar/feeds/";
		return getURL(domain,nextUrl, scope,sequre, session);
	}

	/**
	 * @param nextUrl
	 * @param sequre
	 * @param session
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL getCalendarURL(String nextUrl, boolean sequre, boolean session)
		throws MalformedURLException
	{
		String domain = "default";
		return getCalendarURL(domain,nextUrl,sequre, session);
	}
	
	public static URL getSpreadSheetURL(String domain, String nextUrl, boolean sequre, boolean session) throws MalformedURLException
	{
		String scope = "http://spreadsheets.google.com/feeds/ http://www.google.com/calendar/feeds/";
		return getURL(domain,nextUrl, scope,sequre, session);		
	}
}
