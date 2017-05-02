package com.yordy.ecoresi.api;

import android.util.Log;

import com.yordy.ecoresi.remoting.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by yordy on 31/01/2017.
 */
public class ServerError {
    JSONObject jObjectError = null;
    private String statusCode = null;
    private String name = null;
    private String message = null;
    private Map<String, Object> codes = null;
    private String code = null;

    public ServerError(StackTraceElement[] trace){
        this.getError(trace);
    }

    private void getError(StackTraceElement[] trace){
        Log.e("ServerError", trace[0].getFileName());
        try {
            jObjectError = new JSONObject(trace[0].getFileName());
            JSONObject jObjecte = new JSONObject();
            Log.e("ServerError", jObjecte.toString());
            // statusCode
            if(!jObjectError.isNull("error")){
                jObjecte = jObjectError.getJSONObject("error");
            }

            // statusCode
            if(!jObjecte.isNull("statusCode")){
                String statusCode = jObjecte.getString("statusCode");
                this.setStatusCode(statusCode);
                Log.e("statusCode:", statusCode.toString());
            }
            // name
            if(!jObjecte.isNull("name")){
                String name = jObjecte.getString("name");
                this.setName(name);
                Log.e("name:", name.toString());
            }

            // code
            if(!jObjecte.isNull("code")){
                String code = jObjecte.getString("code");
                this.setCode(code);
                Log.e("code:", code.toString());
            }
            // message
            if(!jObjecte.isNull("message")){
                String message = jObjecte.getString("message");
                this.setMessage(message);
                Log.e("message:", message.toString());
            }
            // details
            if(!jObjecte.isNull("details")){
                JSONObject details = jObjecte.getJSONObject("details");
                Log.e("details:", details.toString());
                JSONObject detailsObj = new JSONObject(details.toString());
                if(!detailsObj.isNull("codes")){
                    JSONObject codes = detailsObj.getJSONObject("codes");
                    Log.e("codes:", codes.toString());
                    Map<String, Object> map = JsonUtil.fromJson(codes);
                    this.setCodes(map);
                    /*for (Map.Entry<String, Object> entry : map.entrySet())
                    {
                        Log.e("error", "campo: "+entry.getKey() + " error: " + entry.getValue());
                    }*/
                }
            }
        } catch (JSONException e) {
            this.setName(trace[0].getFileName());
            Log.e("ServerError", "Verificar en getError(StackTraceElement[] trace)"+trace[0].getFileName());
        }
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getCodes() {
        return codes;
    }

    public void setCodes(Map<String, Object> codes) {
        this.codes = codes;
    }

    public JSONObject getjObjectError() {
        return jObjectError;
    }

    public void setjObjectError(JSONObject jObjectError) {
        this.jObjectError = jObjectError;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
