package org.rmj.guanzongroup.g3msg_notifylib.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;
import org.rmj.guanzongroup.g3msg_notifylib.Fragments.Fragment_NotificationList;
import org.rmj.guanzongroup.g3msg_notifylib.Model.NotificationMessageModel;
import org.rmj.guanzongroup.g3msg_notifylib.R;
import org.rmj.guanzongroup.g3msg_notifylib.Sender.G3_ResponseSender;
import org.rmj.guanzongroup.g3msg_notifylib.Utils.FormatUIText;

public class Activity_NotificationView extends AppCompatActivity {
    private static final String TAG = Activity_NotificationView.class.getSimpleName();
    private NotificationMessageModel poMsgModel;

    private Toolbar toolbar;
    private TextView lblTitle;
    private TextView lblSender;
    private TextView lblDateTime;
    private TextView lblMessage;
    private MaterialButton btnReply;
    private MaterialButton btnCshCt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        poMsgModel = (NotificationMessageModel)getIntent().getSerializableExtra("notification");
        updateMessageData();
        setupWidgets();
        setupData();
    }

    private void setupWidgets(){
        toolbar = findViewById(R.id.toolbar_notificationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lblTitle = findViewById(R.id.lbl_ntf_messageTitle);
        lblSender = findViewById(R.id.lbl_ntf_messageSender);
        lblDateTime = findViewById(R.id.lbl_ntf_messageDateTime);
        lblMessage = findViewById(R.id.lbl_ntf_messageBody);
        btnReply = findViewById(R.id.btn_Reply);
        btnCshCt = findViewById(R.id.btn_cashCount);
    }

    private void setupData(){
        lblTitle.setText(poMsgModel.getMessageTitle());
        lblSender.setText(poMsgModel.getMessageSender());
        lblDateTime.setText(new FormatUIText().getParseDateTime(poMsgModel.getMessageDate()));
        lblMessage.setText(poMsgModel.getMessageBody());
        btnReply.setOnClickListener(new OnButtonClickListener());
        btnCshCt.setOnClickListener(new OnButtonClickListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void updateMessageData(){
        G3_ResponseSender loResponse = new G3_ResponseSender(Activity_NotificationView.this);
        loResponse.sendResponse(new G3_ResponseSender.onSendResponseListener() {
            @Override
            public JSONObject setResponseType() {
                G3_ResponseSender.RESPONSE loJsonResponse = new G3_ResponseSender.RESPONSE();
                return loJsonResponse.READ_RESPONSE(poMsgModel.getMessageID());
            }

            @Override
            public void onSendSuccessResult() {
                new Fragment_NotificationList().getInstance().refreshView();
            }

            @Override
            public void onSendErrorResult(String error) {

            }
        });
    }

    class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.btn_Reply){
                Toast.makeText(getApplicationContext(), "This feature is not available for now.", Toast.LENGTH_SHORT).show();
            } else if(id == R.id.btn_cashCount){

            }
        }
    }
}
