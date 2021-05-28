package org.rmj.g3appdriver.lib.account.ManageAccount;

import org.json.JSONObject;

public interface onSendRequestListener {
    JSONObject setData();
    void onSuccessResult(JSONObject jsonResponse);
    void onErrorResult(String Message);
}
