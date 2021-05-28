package org.rmj.guanzongroup.g3msg_notifylib.Builder;

import android.app.PendingIntent;

import org.json.JSONObject;

public interface onNotificationReceiveListener {
    void onRecievedNotificationListener(String MessageID);
    JSONObject notificationData();
    PendingIntent onCreateNotification();
}
