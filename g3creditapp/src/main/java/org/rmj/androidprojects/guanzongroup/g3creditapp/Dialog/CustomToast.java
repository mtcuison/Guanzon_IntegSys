package org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

public class CustomToast {
    private Context mContext;
    private String lsMessage;
    private TYPE type;

    public CustomToast(Context context, String Message, TYPE type){
        this.mContext = context;
        this.lsMessage = Message;
        this.type = type;
    }

    public void show(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.toast, null, false);
        TextView label = view.findViewById(R.id.lbl_toast_message);
        ImageView icon = view.findViewById(R.id.img_toast);
        label.setText(lsMessage);
        icon.setImageResource(getIcon());
        Toast loToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        loToast.setGravity(Gravity.CENTER, 0, 0);
        loToast.setView(view);
        loToast.show();
    }

    private int getIcon(){
        switch (type){
            case WARNING:
                return R.drawable.ic_warning;
            case INFORMATION:
                return R.drawable.ic_info;
            case SUCCESS:
                return R.drawable.ic_check;
        }
        return 0;
    }

    public enum TYPE{
        WARNING,
        INFORMATION,
        SUCCESS
    }
}
