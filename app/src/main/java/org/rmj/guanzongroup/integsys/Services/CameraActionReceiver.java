package org.rmj.guanzongroup.integsys.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.rmj.guanzongroup.integsys.Activity.MainActivity;

public class CameraActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int CameraInstance = intent.getExtras().getInt("capturetype");
        new MainActivity().getInstance().setCameraInstance(CameraInstance);
    }
}
