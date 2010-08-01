package com.laughstyle.gae.data;

import com.google.gdata.data.extensions.ExtendedProperty;

public class ExtendedPropertyFactory {

	/**
	 * @param key 拡張プロパティのキー
	 * @param value 拡張プロパティの値
	 * @return 生成されたインスタンスの参照
	 */
	public static ExtendedProperty createProperty(String key, String value)
	{
		ExtendedProperty ep = new ExtendedProperty();
		ep.setName(key);
		ep.setValue(value);		
		return ep;
	}
}
