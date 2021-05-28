package org.rmj.g3appdriver.lib.account.AppLogout;

import org.json.JSONObject;

public interface onLogoutListener {
    JSONObject setData();
    void onSuccessResponse();
    void onErrorResponse(String Message);
}
