package com.laughstyle.gae.data;

import com.google.gdata.data.extensions.ExtendedProperty;

public class ExtendedPropertyFactory {

	/**
	 * @param key �g���v���p�e�B�̃L�[
	 * @param value �g���v���p�e�B�̒l
	 * @return �������ꂽ�C���X�^���X�̎Q��
	 */
	public static ExtendedProperty createProperty(String key, String value)
	{
		ExtendedProperty ep = new ExtendedProperty();
		ep.setName(key);
		ep.setValue(value);		
		return ep;
	}
}
