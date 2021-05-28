package org.rmj.g3appdriver.lib.account.AppLogin;

import org.json.JSONObject;

public interface onUserLoginListener {
    JSONObject setParams();
    void onSuccessLogin();
    void onLoginErrorResult(String Message);
}
