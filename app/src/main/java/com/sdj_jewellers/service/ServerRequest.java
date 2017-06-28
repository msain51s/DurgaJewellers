package com.sdj_jewellers.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;

@SuppressWarnings("deprecation")
public class ServerRequest {

	public static void postRequest(final String url, final JSONObject json,
			final ResponseListener r, final int rid) {
		System.out.println("");
		new Thread(new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
					Response response = null;
					HttpClient httpClient = new DefaultHttpClient();

					HttpPost post = new HttpPost(url);
					StringEntity stringEntity = new StringEntity(
							json.toString(), "UTF-8");

					stringEntity.setContentType(new BasicHeader(
							HTTP.CONTENT_TYPE, "application/json"));
					post.setHeader("Accept", "application/json");
					post.setHeader("Content-type", "application/json;");
					post.setEntity(stringEntity);
					
					HttpResponse res = httpClient.execute(post);
					StatusLine sl = res.getStatusLine();
					// status = sl.getStatusCode();
					if (sl.getStatusCode() == 200) {
						HttpEntity entity = res.getEntity();

						InputStream in = entity.getContent();
						String resp = getString(in);

						System.out.println("parser"+resp);

						// status = resp;
						in.close();
						if (isError(resp)) {
							response = new Response(getErrorMsg(resp), true);
						} else
							response = new Response(resp);
						    r.onResponse(response, rid);

						return;
					} else if (sl.getStatusCode() == 400) {
						r.onResponse(new Response(getErrorResponse(rid), true),
								rid);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				r.onResponse(new Response(getErrorResponse(-1), true), rid);
			}
		}).start();

	}


	// Image Upload method

	public static void postFile(final File file, final String url, final ResponseListener r, final int rid)
	{
		new Thread(new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {

				Response response = null;
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost request = new HttpPost(url);
				FileEntity reqEntity = new FileEntity(file, "application/zip");
				request.setEntity(reqEntity);

				HttpResponse res;

				try {
					res = httpclient.execute(request);
					StatusLine sl = res.getStatusLine();
					if (sl.getStatusCode() == 200) {
						HttpEntity entity = res.getEntity();

						InputStream in = entity.getContent();
						String resp = getString(in);

						System.out.println("parser" + resp);

						// status = resp;
						in.close();
						if (isError1(resp)) {
							response = new Response(getErrorMsg(resp), true);
						} else
							response = new Response(resp);
						// response = new Response(resp);
						    r.onResponse(response, rid);

						return;
					} else if (sl.getStatusCode() == 400) {
						r.onResponse(new Response(getErrorResponse(rid), true),
								rid);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				r.onResponse(new Response(getErrorResponse(-1), true), rid);

				httpclient.getConnectionManager().shutdown();
			}
		}).start();
	}

	// Multipart Image Upload code
	/*public static void postMultipart(final File file, final String url, final ResponseListener r, final int rid) {
		new Thread(new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {

				Response response = null;
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost request = new HttpPost(url);
				MultipartEntity reqEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);
				FileBody fileBody=new FileBody(file);
				reqEntity.addPart("Image", fileBody);
				try {
					reqEntity.addPart("ImageName", new StringBody("MultiPartImage"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				request.setEntity(reqEntity);

				HttpResponse res;

				try {
					res = httpclient.execute(request);
					StatusLine sl = res.getStatusLine();
					if (sl.getStatusCode() == 200) {
						HttpEntity entity = res.getEntity();

						InputStream in = entity.getContent();
						String resp = getString(in);

						System.out.println("parser" + resp);

						// status = resp;
						in.close();
						if (isError1(resp)) {
							response = new Response(getErrorMsg(resp), true);
						} else
							response = new Response(resp);
						// response = new Response(resp);
						r.onResponse(response, rid);

						return;
					} else if (sl.getStatusCode() == 400) {
						r.onResponse(new Response(getErrorResponse(rid), true),
								rid);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				r.onResponse(new Response(getErrorResponse(-1), true), rid);

				httpclient.getConnectionManager().shutdown();
			}
		}).start();

	}
*/

	private static String getString(InputStream in) {
		int c = -1;
		StringBuffer r = new StringBuffer();

		try {
			while ((c = in.read()) != -1)
				r.append((char) c);

			return r.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}



	private static boolean isError(String resp) {
		try {
			/*JSONArray obj = new JSONArray(resp);*/
			@SuppressWarnings("unused")
			JSONObject onjjj = new JSONObject(resp);//obj.getJSONObject(0);

			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


	
	private static boolean isError1(String resp) {
		try {
			if(resp!=null)

			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	

	private static String getErrorMsg(String resp) {
		String msg = "Service is down, please check again later";
		try {
			
			if ( resp.equalsIgnoreCase("[]")|| resp==null) {
				if(resp==null){}
				else
				msg = "No Record Found ";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return msg;
	}


	public static String getErrorResponse(int rid) {

		if (rid == -1) {
			return " Service is down, please try again later";
		}
		return "";
	}

}
