package com.laughstyle.gae;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.util.AuthenticationException;

public class SessionTokenFactory {

	/**
	 * @param query AuthSub�F�،��Query������
	 * @return
	 */
	public static String getSessionToken(String query)
	{
		String singleUseToken = AuthSubUtil.getTokenFromReply(query);
		String sessionToken = null;
		
		try{
			sessionToken = AuthSubUtil.exchangeForSessionToken(singleUseToken, null);
		}
		catch(AuthenticationException e)
		{
			
		}
		catch(GeneralSecurityException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		
		return sessionToken;
	}
	
	public static String  getSessionToken(String query, String pkPath)
	{
		PrivateKey pk = getPrivateKey(pkPath);
		
		String singleUseToken = AuthSubUtil.getTokenFromReply(query);
		String sessionToken = null;
		
		try{
			sessionToken = AuthSubUtil.exchangeForSessionToken(singleUseToken, pk);
		}
		catch(AuthenticationException e)
		{
			
		}
		catch(GeneralSecurityException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		
		return sessionToken;	
	}
	
	public static PrivateKey getPrivateKey(String pkPath)
	{
		PrivateKey privateKey = null;

		if (privateKey == null) {
			try {
				privateKey = AuthSubUtil.
				getPrivateKeyFromKeystore(pkPath,
											"tiger789",
											"Example",
											"tiger789");
			} catch (Exception e) {
				throw new RuntimeException("Error reading from keystore file - ", e);
			}
		}
		return privateKey;
	}
}
