package com.laughstyle.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthSigner;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import com.laughstyle.gae.AuthSubURLFactory;
import com.laughstyle.gae.SessionTokenFactory;

public class SpreadDemo extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		doPost(req, resp);
	}
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {

		String query = req.getQueryString();
		if(null == query)
		{
//			login(req, resp);
		
			String nextUrl = req.getRequestURL().toString();
			
			String domain = "laughstyle-office.com";
			if(null == domain)
				domain = "default";
				
			URL url = AuthSubURLFactory.getSpreadSheetURL(domain, nextUrl, true, true);
			req.setAttribute("logonurl", url);

			resp.sendRedirect(url.toString());
			
			return;
		}
		else
		{
			PrintWriter out = resp.getWriter();

			String pkPath = this.getServletContext().getRealPath("/Example.jks");
			
			String token = null;
			if(null == pkPath)
			{
				token = SessionTokenFactory.getSessionToken(query);
			}
			else
			{
				token = SessionTokenFactory.getSessionToken(query, pkPath);
			}
			req.setAttribute("token", token);
			
			SpreadsheetService service = new SpreadsheetService("exampleCo-exampleApp-1");
			service.setAuthSubToken(token, null);
/*
			GoogleOAuthParameters params = new GoogleOAuthParameters();
			params.setOAuthConsumerKey("lso-demo.appspot.com");
			params.setOAuthToken(token);
			params.setOAuthSignatureMethod("HMAC-SHA1");
			params.setScope("http://spreadsheets.google.com/feeds/");
			params.setOAuthConsumerSecret("2hv5cirusCDZjWlitEPzB3Ri");	
			
			OAuthSigner signer = new OAuthHmacSha1Signer();
			GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(signer);
			try {
				token = oauthHelper.getAccessToken(params);
				service.setAuthSubToken(token, null);
				service.setOAuthCredentials(params, signer);
			} catch (OAuthException e2) {
				// TODO 自動生成された catch ブロック
				e2.printStackTrace();
			}
*/			
			URL metafeedUrl = new URL("http://spreadsheets.google.com/feeds/spreadsheets/private/full");
			SpreadsheetFeed feed = null;
			try {
				feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
			} catch (ServiceException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			if(null == feed)
			{
				return;
			}

			List<SpreadsheetEntry> spreadsheets = feed.getEntries();
			for (int i = 0; i < spreadsheets.size(); i++) {
				SpreadsheetEntry entry = spreadsheets.get(i);

				WorksheetFeed worksheetFeed = null;
				try {
					worksheetFeed = entry.getService().getFeed(entry.getWorksheetFeedUrl(), WorksheetFeed.class);
				} catch (ServiceException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}
				List<WorksheetEntry> worksheets = worksheetFeed.getEntries();			

				for (WorksheetEntry worksheet: worksheets) {
					String title = worksheet.getTitle().getPlainText();
					int rowCount = worksheet.getRowCount();
					int colCount = worksheet.getColCount();
					out.println("\t" + title + "- rows:" + rowCount + " cols: " + colCount);
				}
			}
			
		}
	} 
	private void login(HttpServletRequest req, HttpServletResponse resp)
	  {
	    try
	    {
			//パラメータを設定する
			GoogleOAuthParameters params = new GoogleOAuthParameters();
			params.setOAuthConsumerKey("lso-demo.appspot.com");
			params.setOAuthConsumerSecret("2hv5cirusCDZjWlitEPzB3Ri");

			//Step1～2：リクエストトークンを取得する
			GoogleOAuthHelper helper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
//			helper.getUnauthorizedRequestToken(params);

			//	      params.setOAuthSignatureMethod("HMAC-SHA1");
	      params.setScope("https://spreadsheets.google.com/feeds/ https://www.google.com/calendar/feeds/");

	      helper.getUnauthorizedRequestToken(params);
	      String authUrls = helper.createUserAuthorizationUrl(params);	      
	      
	      System.out.println(String.format("OAuth token:%s", params.getOAuthToken()));
	      System.out.println(String.format("OAuth token Sercet:%s", params.getOAuthTokenSecret()));
	      
	      //以下、Step3～6
	      
	      //TokenSecretを取得する
	      String tokenSecret = "?oauth_token_secret=" + URLEncoder.encode(params.getOAuthTokenSecret(), "UTF-8");
	      
	      //開発環境の場合
	      if(req.getServerName().equals("localhost"))
	      {
	        params.setOAuthCallback("http://localhost:8888/demo/spreaddemo" + tokenSecret);
	      }
	      //App Engineの場合
	      else
	      {
		        params.setOAuthCallback("https://lso-deo.appspot.com/demo/spreaddemo" + tokenSecret);
//		        params.setOAuthCallback(String.format("https://lso-deo.appspot.com/demo/spreaddemo",
//			            URLEncoder.encode(tokenSecret, "UTF-8")));
	      }     
	      
	      //認証用URLを作成する
	      String authUrl = helper.createUserAuthorizationUrl(params);
	      
	      //認証ページにリダイレクトする
	      resp.sendRedirect(authUrl);
	    }
	    catch(Exception ex)
	    {
	      resp.setContentType("text/html;charset=UTF-8");
	      
	      try
	      {
	        resp.getWriter().println(String.format("<html><body>%s</body></html>",ex.toString()));
	      }
	      catch(Exception e){}
	    }  
	  }	
}
