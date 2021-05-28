package org.rmj.guanzongroup.integsys.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.cardview.widget.CardView;

import org.rmj.g3cm.android.g3cashmanager.Activity_CashCounter;
import org.rmj.guanzongroup.integsys.R;

public class CashCountSelect {
    private Context mContext;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private View mView;
    private CardView btnCashCount;
    private CardView btnSettings;
    private Button btnCancel;

    public CashCountSelect(Context context, View view){
        this.mContext = context;
        this.mView = view;
        this.builder = new AlertDialog.Builder(mContext);
    }

    public void showDialog(){
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_cashcount, null, false);
        builder.setCancelable(false);
        builder.setView(mView);

        btnCashCount = mView.findViewById(R.id.btn_dialogCashCount);
        btnSettings = mView.findViewById(R.id.btn_dialogSettings);
        btnCancel = mView.findViewById(R.id.btn_dialogCancel);

        btnCashCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, Activity_CashCounter.class));
                dialog.dismiss();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCashCounter = new Intent(mContext, Activity_CashCounter.class);
                intentCashCounter.putExtra("OpenDialog", true);
                mContext.startActivity(intentCashCounter);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }
}
