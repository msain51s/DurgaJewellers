package com.sdj_jewellers.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Response {

    public static final String KEY_MESSAGE = "message";
    protected String msg;
    String data;
    protected String successmsg="";
    boolean isError = false;

    public Response(String data) throws Exception {
        this.data = data;
        try {

            Object json = new JSONTokener(data).nextValue();
            if (json instanceof JSONObject)
            {
                JSONObject doc = new JSONObject(data);
                if(doc.has("msg"))
                successmsg = doc.getString("msg");
            }
            else if (json instanceof JSONArray)
            {
                JSONArray docarray = new JSONArray(data);
                JSONObject doc =docarray.getJSONObject(0);
                if(doc.has("msg"))
                successmsg = doc.getString("msg");
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    public Response(String msg, boolean isError) {
       
        this.msg = msg;
        this.isError = isError;
    }

    public String getSuccessMessage()
    {
    	return successmsg;
    }
    /**
     *
     *
     * @return true if error is returned from server
     */
    public boolean isError() {
        return isError;
    }

    /**
     *
     *
     * @return error message
     */
    public String getErrorMsg() {
        return msg;
    }

    /**
     *
     *
     * @return server response
     */
    public String getData() {
        return data;
    }
}
