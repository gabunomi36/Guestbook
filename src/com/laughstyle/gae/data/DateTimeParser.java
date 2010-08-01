package com.laughstyle.gae.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.google.apphosting.api.ApiProxy.ArgumentException;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.introspection.IServiceDocument;

public class DateTimeParser {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static DateTime parse(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) throws ParseException
	{
		String dateTimeString = String.format("%d-%02d-%02d %02d:%02d:%02d", year,month,dayOfMonth,hourOfDay,minute,second);
		return parse(dateTimeString);
	}
	
	public static DateTime parse(Calendar cal) throws ParseException
	{
		return parse(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));
	}
	
	public static DateTime parse(String dateString, String timeString) throws ParseException
	{
		String dateTimeString;
		
		if(null == dateString || "" == dateString || "null" == dateString)
		{
			throw new ArgumentException(DateTimeParser.class.getPackage().getName(), "parse");
		}
		
		if(null == timeString || "" == timeString || "null" == timeString)
		{
			dateTimeString = String.format("%s 00:00", dateString);
		}
		else
		{
			dateTimeString = String.format("%s %s", dateString, timeString);
		}

		return parse(dateTimeString);
	}

	public static DateTime parse(String dateTimeString) throws ParseException
	{
		DateTime dt = null;
		dt = new DateTime(dateFormat.parse(dateTimeString));
		return dt;
	}
	
	public static String[] split(String dateTimeString)
	{
		String[] datetime = dateTimeString.split(" ");
		
		String[] result = new String[2];
		result[0] = datetime[0];
		
		if(datetime.length > 1)
			result[1] = datetime[1];
		else
			result[1] = null;

		return result;
	}
}
