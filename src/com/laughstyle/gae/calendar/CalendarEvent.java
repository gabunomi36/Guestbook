package com.laughstyle.gae.calendar;

import java.net.*;

/**
 * @author touma
 *
 */
public class CalendarEvent
{
	private URL url;
	private String title;
	private String content;
	private String where;
	private String ext;
	private String etag;
	
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;

	public CalendarEvent()
	{
		
	}

	public CalendarEvent(URL url, String title, String content, String startDate, String startTime, String endDate, String endTime, String where, String ext, String etag)
	{
		this.url = url;
		this.setTitle(title);
		this.setContent(content);
		this.setWhere(where);
		this.setExt(ext);
		this.setEtag(etag);
		this.setStartDate(startDate);
		this.setStartTime(startTime);
		this.setEndDate(endDate);
		this.setEndTime(endTime);
	}
	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return
	 */
	public String getContent() {
		return content;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getWhere() {
		return where;
	}

	public void setUrl(String url) {
		try{
			this.url = new URL(url);
		}
		catch(Exception e)
		{
			this.url = null;
		}
	}
	public URL getUrl() {
		return url;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getExt() {
		return ext;
	}
	public void setEtag(String etag) {
		this.etag = etag;
	}
	public String getEtag() {
		return etag;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndTime() {
		return endTime;
	}
}
