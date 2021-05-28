package org.rmj.g3cm.android.g3cashmanager.kwiksearch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.lib.cashcount.RequestKwikSearch;
import org.rmj.g3cm.android.g3cashmanager.Activity_CashCountSubmit;
import org.rmj.g3cm.android.g3cashmanager.R;
import org.rmj.g3cm.android.g3cashmanager.kwiksearch.adapter.Adapter_RequestNames;
import org.rmj.g3cm.android.g3cashmanager.kwiksearch.adapter.RequestNames;

import java.util.ArrayList;
import java.util.List;

public class KwikSearchDialog {
    private Context mContext;
    private View mView;
    private ImageButton btnClose;

    private LayoutInflater inflater;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private Toast toast;
    private Handler handler;
    private Runnable runnable;
    private String name;

    private List<RequestNames> requestNamesList = new ArrayList<>();
    private Adapter_RequestNames custAdapter;

    public KwikSearchDialog(Context context, View view){
        this.mContext = context;
        this.mView = view;
        this.dialogBuilder = new AlertDialog.Builder(mContext);
        this.inflater = LayoutInflater.from(mContext);
        this.progressDialog = new ProgressDialog(mContext);
    }

    public void createKwikSearch(String NameSearch){
        this.name = NameSearch;
        showDialog();
    }

    private void createDialog(){
        mView = inflater.inflate(R.layout.dialog_kwiksearch, null, false);
        dialogBuilder.setCancelable(false)
                .setView(mView);
        dialog = dialogBuilder.create();
        dialog.setCancelable(false);
    }

    private void getKwikSearchList(String Name){
        new RequestKwikSearch().requestKwikSearchList(mContext, getParameter(Name), new RequestKwikSearch.onReceiveKwikSearchList() {
            @Override
            public void onReceiveList(ArrayList<String[]> kwikSearchList) {
                handler.post(runnable);
                handler.removeCallbacks(runnable);
                progressDialog.dismiss();
                for(int x = 0; x<kwikSearchList.size(); x++){
                    RequestNames requestNames = new RequestNames(kwikSearchList.get(x)[1], kwikSearchList.get(x)[0], kwikSearchList.get(x)[2]);
                    requestNamesList.add(requestNames);
                }
                recyclerView = mView.findViewById(R.id.rclrvw_requestNames);
                btnClose = mView.findViewById(R.id.btn_dialogClose);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                custAdapter = new Adapter_RequestNames(mContext,requestNamesList);
                custAdapter.setOnItemClickListener(new Adapter_RequestNames.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        new Activity_CashCountSubmit().getInstance().setName(requestNamesList.get(position).getRequestName());
                        dialog.dismiss();
                    }
                });
                dismissDialog();
                recyclerView.setAdapter(custAdapter);
                recyclerView.setLayoutManager(layoutManager);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
            }

            @Override
            public void onErrorReceive(String errorMessage) {
                handler.post(runnable);
                handler.removeCallbacks(runnable);
                dismissDialog();
                toast = Toast.makeText(mContext, "Something went wrong. Error message : " + errorMessage, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private JSONObject getParameter(String Name){
        JSONObject object = new JSONObject();
        try{
            object.put("reqstdnm", Name);
            object.put("bsearch", true);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    private void showDialog(){
        progressDialog.setIcon(R.drawable.guanzo_small_logo);
        progressDialog.setTitle("KwikSearch");
        progressDialog.setMessage("Sending request. Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                createDialog();
                getKwikSearchList(name);
            }
        };
        handler.postDelayed(runnable, 100);
    }

    private void dismissDialog(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
