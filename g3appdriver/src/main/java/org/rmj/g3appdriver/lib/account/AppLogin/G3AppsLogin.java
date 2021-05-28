package org.rmj.g3appdriver.lib.account.AppLogin;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;

public interface G3AppsLogin {
    void loginUser(Context context, HashMap headers, JSONObject Parameters);
    String getResult();
    String getMessage();
}
