package org.rmj.g3appdriver.lib.telecom;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.utils.Http.WebClient;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.rmj.g3appdriver.etc.SQLiteHandler.TAG;


public class KnoxApproval {
    private String dialog_result = "";

    private String message = "";
    private String imei;
    private String clientid;
    private String productid;
    private String userid;
    private String logno;
    private String token;
    Context ctx;

    public KnoxApproval(Context sctx, String simei, String sclientid, String sproductid, String suserid, String slogno, String stoken){
        this.ctx = sctx;
        this.imei = simei;
        this.clientid = sclientid;
        this.productid = sproductid;
        this.userid = suserid;
        this.logno = slogno;
        this.token = stoken;

        Log.d(TAG,"Headers: IMEI-" + imei +" CLIENTID-"+clientid+" PRODUCTID-"+productid+" USERID-"+userid+" LOGNO-"+logno+" TOKEN-"+token);

    }

    //result for dialog
    public String getResult(){
        return dialog_result;
    }

    //getting message
    public String getMessage(){
        return message;
    }
    //imei Authorization

    //old version (not in use)
    public void isAuthorized(final String device_imei, final String deviceUID, final String approveComment){

        String sURL = "https://restgk.guanzongroup.com.ph/security/knoxauthactivate.php";
        Calendar calendar = Calendar.getInstance();

        Map<String, String> headers =new HashMap<String, String>();

        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", productid);
        headers.put("g-api-imei", imei);
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();
        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-client", clientid);
        headers.put("g-api-user", userid);
        headers.put("g-api-log", logno);
        headers.put("g-api-token", token);

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("imei", device_imei);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;

        String response = null;
        try {
            Log.d(TAG, " URL ACTIVATION: " + sURL);
            response = WebClient.httpsPostJSon(sURL, param.toString(), (HashMap<String, String>) headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
            if(System.getProperty("store.error.info").equals("404")){
                message = "Authorization Error " + System.getProperty("store.error.info") + ": Please make sure internet is stable. ";
                dialog_result = "error";
            }else{
                message = "Authorization Error " +  System.getProperty("store.error.info") + ": Please make sure the internet connection is stable.";
                dialog_result = "error";
            }
        }else{
            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                if(result.equals("success")){
                    //message = result;
                    String knox_base = jObj.getString("knox_base");
                    String knox_url = jObj.getString("knox_url");
                    String knox_api = jObj.getString("knox_api");

                    String url_activation =knox_base + knox_url;
                    String sUrl  =  url_activation.replace("\\", "");
                    message = sUrl;
                    deviceApproved(sUrl, knox_api, deviceUID, approveComment);

                }else{
                    //message = "This device is not authorized.";
                    Log.d(TAG, "Else Error: " + response);

                    message = "Your device: " + device_imei +" is not authorized. ";
                    dialog_result = "unauthorize";
                }
            } catch (JSONException e) {
                message = "Please make sure the internet connection is stable.";
                dialog_result = "error";

            }
        }
    }
    //old version (not in use)
    public void deviceApproved(final String url_activation, final String knox_api, final String deviceUid, final String approveComment){

        String sURL = url_activation;
        String transactionId = "M00118000001";

        Map<String, String> headers =new HashMap<String, String>();

        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("x-knox-apikey", knox_api);
        headers.put("x-knox-transactionId",transactionId);

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("deviceUid", deviceUid);
            param.put("approveComment", approveComment);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;

        String response = null;
        try {

            response = WebClient.httpsPostJSon(sURL, param.toString(), (HashMap<String, String>) headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));

            if(System.getProperty("store.error.info").equals("404")){
                message = "Error in activating this IMEI: " + deviceUid + ". This device seems to be not uploaded yet. Please contact Mr. Jeff Yambao in this number : 09177013632 for further assistance,";
                dialog_result = "error";

            }else if(System.getProperty("store.error.info").equals("400")){
                message = "This IMEI " + deviceUid + " could be activated already. Please contact Mr. Jeff Yambao in this number : 09177013632 for further assistance," ;
                dialog_result = "error";
            }
            else{
                message = "Activation Error " + System.getProperty("store.error.info") + ": Please contact Mr. Jeff Yambao in this number : 09177013632 for further assistance,";
                dialog_result = "error";
            }

        }else{
            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                if(result.equalsIgnoreCase("success")){
                    //message = response.toString();
                    message = "This IMEI: " + deviceUid + " was succesfully activated.";
                    dialog_result = "success";

                }else{
                    //message = "This device is not authorized.";
                    //message = response.toString();
                    message = "This IMEI: " + deviceUid + " was not activated.";
                    dialog_result = "error";
                }
            } catch (JSONException e) {
                message = "Please make sure the internet connection is stable.";
                dialog_result = "error";
            }
        }
    }

    //new version of activation
    public void knoxApproval(final String request, final JSONObject parameters){

        Log.d(TAG, "Passed Values:  Request: " + request + " Json Parameters: " + parameters);

        String sURL = "https://restgk.guanzongroup.com.ph/samsung/knox.php";
        Calendar calendar = Calendar.getInstance();

        Map<String, String> headers =new HashMap<String, String>();

        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", productid);
        headers.put("g-api-imei", imei);
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();
        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-client", clientid);
        headers.put("g-api-user", userid);
        headers.put("g-api-log", logno);
        headers.put("g-api-token", token);

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("request", request);
            param.put("param", parameters.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Knox Approval Params: " + param);


        JSONObject json_obj = null;

        String response = null;
        try {
            //Log.d(TAG, " URL ACTIVATION: " + sURL);
            response = WebClient.httpsPostJSon(sURL, param.toString(), (HashMap<String, String>) headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
            if(System.getProperty("store.error.info").equals("404")){
                message = "Authorization Error " + System.getProperty("store.error.info");
                dialog_result = "error";
            }else{
                message = "Authorization Error " +  System.getProperty("store.error.info");
                dialog_result = "error";
            }
        }else{
            Log.d(TAG, "Response From Activation: " + response);

            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                if(result.equalsIgnoreCase("success")){
                    //message = response.toString();
                    message = "Imei is succesfully activated.";
                    dialog_result = "success";

                }else{
                    JSONObject jObj_err =  new JSONObject(jObj.getString("error"));
                    String err_code = jObj_err.getString("code");
                    Log.d(TAG, "Else Error: " + response);

                    getErrMsg(err_code);
                    //message = err_code;
                }
            } catch (JSONException e) {
                message = response;
                dialog_result = "error";
            }

        }
    }

    private String getErrMsg(final String err_code){

        switch(err_code){
            //HTTP Error Code
            case "400":
                message = "Code 400 (Bad Request) : The client has issued an invalid request. ";
                break;
            case "401":
                message = "Error Code 401 (Unauthorized) : Authorization for the API is required, but the request has not been authenticated.";
                break;
            case "403":
                message = "Error Code 403 (Forbidden) : The request has been authenticated but does not have the appropriate permissions, or the requested resource cannot be found.";

            case "404":
                message = "Error Code 404 (Not Found): Requested path does not exist.";
                break;
            case "406":
                message = "Error Code 406 (Not Acceptable): The client has requested a MIME type via the Accept header for a value not supported by the server.";
                break;
            case "422":
                message = "Error Code 422 (Unprocessable Entity): The client has made a valid request, but the server cannot process it. This is often returned for APIs when certain limits have been exceeded.";
                break;
            case "429":
                message = "Error Code 429 (Too Many Requests): The client has exceeded the number of requests allowed for a given time window.";
                break;
            case "500":
                message = "Error Code 500 (Internal Server Error): An unexpected error on the SmartThings servers has occurred. ";
                break;
            case "501":
                message = "Error Code 422 (Not Implemented): The client request was valid and understood by the server, but the requested feature has yet to be implemented.";
                break;

            //Message and description
            case "4010000":
                message = "Error Code 4010000 (Authorization Fail): The API key is not valid or the restriction data does not match.";
                break;
            case "4002102":
                message = "Error Code 4002102 (API Key Restriction Invalid): IP address or http referer format are incorrect.";
                break;
            case "4042101":
                message = "Error Code 4002102 (API Key Restriction Not Found): The IP address or http referer are empty.";
                break;
            case "4040100":
                message = "Error Code 4040100 (User Not Found): There is no user linked to the API key.";
                break;
            case "4040900":
                message = "Error Code 4040900 (Tenant Not Found): There is no tenant linked to the API key.";
                break;
            case "4000000":
                message = "Error Code 4000000 (Resource Invalid Parameters): Argument is invalid.";
                break;
            case "4001809":
                message = "Error Code 4001809 (License Is Lack): There are no additional licenses that can be activated.";
                break;
            case "4040300":
                //message = "Error Code 4040300 (Device Not Found): A device was not found with the specified condition.";
                message = "Specified Device IMEI was not found. Please contact MIS Support for uploading Device IMEI.";
                break;
            case "4000310":
                //message = "Error Code 4040300 (Device State Invalid): The operation is not permitted, in the current state.";
                message = "Operation cannot proceed. Device IMEI was already activated. Please enroll your the device.";
                break;
            case "4000316":
                message = "Error Code 4000316 (Device Bulk Operation Limit Exceeded): The bulk operation limit has been exceeded.";
                break;
            case "4040400":
                message = "Error Code 4040400 (Profile Not Found): Internal profile not found.";
                break;
            case "4001805":
                message = "Error Code 4001805 (License Name Already Exceed): License name already exists.";
                break;
            case "5001804":
                message = "Error Code 5001804 (License Internal Server Failed): Error communication with the license server.";
                break;
            case "4001813":
                message = "Error Code 4001813 (License Key Already Exist In Oher Region): This license key is already registered to another region (EU/US).";
                break;
            case "4001802":
                message = "Error Code 4001802 (License Max Trial License Count Reached): Max trial license count reached.";
                break;
            case "4041800":
                message = "Error Code 4041800 (License Not Found): License not found.";
                break;
            case "4001806":
                message = "Error Code 4001806 (License Device Mapping Found): This device is already registered with a license.";
                break;
            case "4001812":
                message = "Error Code 4001812 (License ALready Deleted): License already deleted.";
                break;
            default:
                message = "Error Code Not Specified.";
        }

        return message;

    }
}
