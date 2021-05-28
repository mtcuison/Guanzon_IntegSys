package org.rmj.guanzongroup.g3msg_notifylib.Sender;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Http.HttpRequestUtil;
import org.rmj.g3appdriver.utils.Http.RequestHeaders;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.guanzongroup.g3msg_notifylib.Api.ApiConfig;
import org.rmj.guanzongroup.g3msg_notifylib.DbHelper.NotificationDataHelper;

import java.util.Calendar;
import java.util.HashMap;

public class G3_ResponseSender {
    private static final String TAG = G3_ResponseSender.class.getSimpleName();
    private Context mContext;
    private NotificationDataHelper helper;
    private RequestHeaders headers;
    private ConnectionUtil connectionUtil;
    private static String MessageID = "";
    private onSendResponseListener mListener;
    private static int ResponseType = 0;

    public G3_ResponseSender(Context context){
        this.mContext = context;
        this.helper = new NotificationDataHelper(mContext);
        this.headers = new RequestHeaders(mContext);
        this.connectionUtil = new ConnectionUtil(mContext);
    }
    /**
     * This is use to send a response to the sender..
     *
     * HttpRequestUtil uses the Webclient Class as http json reqquest
     * Headers is required to send the parameters
     * Data is the parameters that are going to be send
     * onResponse gathers the response message from the server.
     * this returns success/error, this response are being held by the HttpRequestUtil
     * onErrorMessage has parameter when onResponse result == error*/
    public void sendResponse(onSendResponseListener listener){
        this.mListener = listener;
        /**
         * HttpRequestUtil is a class base from class WebClient.
         * HttpRequestUtil automatically get and set the data to
         * the webclient class, server response listeners are interfaces
         * that handles the JSONObject response from the server.*/
        if(connectionUtil.isDeviceConnected()) {
            new HttpRequestUtil().sendRequest(ApiConfig.SEND_RESPONSE, new HttpRequestUtil.onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    /**
                     * JSONObject data are set in this area of code.
                     * this class passess the data to the WebClient...*/
                    return mListener.setResponseType();
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    if (mListener != null) {
                        mListener.onSendSuccessResult();
                        if (ResponseType == 0) {
                            helper.updateNotificationStatus(MessageID, 2);
                        }
                        if (ResponseType == 1) {
                            helper.updateNotificationStatus(MessageID, 3);
                        }
                    }
                    Log.e(TAG, "Notification response has sent successfully.");
                }

                @Override
                public void onErrorResponse(String message) {
                    if (mListener != null) {
                        mListener.onSendErrorResult(message);
                    }
                    Log.e(TAG, "Something went wrong..." + message);
                }
            });
        } else {
            helper.updateOfflineNotificationStatus(MessageID, 3);
            if (mListener != null) {
                mListener.onSendErrorResult("Your are currently offline.");
            }
        }
    }


    /**
     * Class that returns what response
     * type must be send to the sender.
     *
     * this class creates the parameters needed to send.*/
    public static class RESPONSE{
        Calendar calendar = Calendar.getInstance();

        /**
         * Creates a JSONObject that has the Delivered/Receive response parameters.
         * this is to identify tha the end user/recipient has read the
         * message/notification being sent by the sender.*/
        public final JSONObject RECEIVED_RESPONSE(String MessageIDxx){
            MessageID = MessageIDxx;
            try {
                JSONObject params = new JSONObject();
                params.put("transno",MessageID);
                params.put("status", "2");
                params.put("stamp", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
                params.put("infox", "");
                ResponseType = 0;
                return params;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Creates a JSONObject that has the READ response parameters.
         * this is to identify tha the end user/recepient has read the
         * message/notification being sent by the sender.*/
        public final JSONObject READ_RESPONSE(String MessageIDxx){
            MessageID = MessageIDxx;
            try {
                JSONObject params = new JSONObject();
                params.put("transno", MessageIDxx);
                params.put("status", "3");
                params.put("stamp", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
                params.put("infox", "");
                ResponseType = 1;
                return params;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public interface onSendResponseListener{
        JSONObject setResponseType();
        void onSendSuccessResult();
        void onSendErrorResult(String error);
    }
}
