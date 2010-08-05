package com.laughstyle.gae;

import java.io.IOException;
import java.net.URLDecoder;
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
// 緊急パッチ			
			singleUseToken = URLDecoder.decode(singleUseToken, "UTF-8");
			sessionToken = AuthSubUtil.exchangeForSessionToken(singleUseToken, null);
		}
		catch(AuthenticationException e)
		{
			System.out.println(e.toString());
			
		}
		catch(GeneralSecurityException e)
		{
			System.out.println(e.toString());
			
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
			
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
