package com.laughstyle.gae.data;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextConstruct;

public class LSConverter {

	public static TextConstruct String2TextConstruct(String value)
	{
		return new PlainTextConstruct(value);
	}
}
