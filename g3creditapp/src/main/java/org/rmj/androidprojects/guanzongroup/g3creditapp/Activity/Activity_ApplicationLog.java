package org.rmj.androidprojects.guanzongroup.g3creditapp.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.ApplicationLogAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.CustomToast;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.CreditAppLocalData;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.GOCASExport;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditImportApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.Applications.CreditSendApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData.ApplicantLocalData;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.Objects;

public class Activity_ApplicationLog extends AppCompatActivity {
    private static final String TAG = Activity_ApplicationLog.class.getSimpleName();

    private Handler poHandler;
    private Runnable poRun;
    private ProgressDialog poDialog;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ApplicationLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupClass();
        poDialog.setTitle("Application Log");
        poDialog.setMessage("Updating list. Please wait...");
        poDialog.setIcon(R.drawable.guanzo_small_logo);
        poDialog.setCancelable(false);
        poDialog.show();
        poRun = this::UpdateLocalData;
        poHandler.postDelayed(poRun, 500);
        setContentView(R.layout.activity_application_log);

        Toolbar toolbar = findViewById(R.id.toolbar_applicationLog);
        toolbar.setTitle("Credit Applications");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview_ApplicationLog);
        TextInputEditText tieSearch = findViewById(R.id.tie_cap_applicant_search);
        tieSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapter.getApplicantionFilter().filter(s.toString());
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Activity_ApplicationLog.this, "Unknown error occurred. Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private void setupClass(){
        poDialog = new ProgressDialog(Activity_ApplicationLog.this);
        poHandler = new Handler();
        layoutManager = new LinearLayoutManager(Activity_ApplicationLog.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void UpdateLocalData(){
        new CreditSendApplication(Activity_ApplicationLog.this).sendApplications();
        new CreditImportApplication(Activity_ApplicationLog.this).importApplications(new CreditImportApplication.OnImportCreditApplication() {
            @Override
            public void onSuccessImportResult() {

            }

            @Override
            public void onFailedImportResult(String errorMessage) {

            }
        });
        setupList();
        poHandler.post(poRun);
        poHandler.removeCallbacks(poRun);
        poDialog.dismiss();
    }

    private void setupList(){
        adapter = new ApplicationLogAdapter(new ApplicantLocalData(Activity_ApplicationLog.this).getApplicationLogList());
        adapter.setOnVoidApplicationListener((position, TransNox) -> new CreditApplication(Activity_ApplicationLog.this).voidApplication(TransNox, Message -> {
            Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
            setupList();
        }));

        adapter.setOnExportGOCASListener((GOCAS, ClientName, DateApplied) -> {
            try{
                new GOCASExport().WriteJson(GOCAS, ClientName, DateApplied);
                new CustomToast(this, "GOCAS info exported successfully.", CustomToast.TYPE.SUCCESS).show();
            } catch (Exception e){
                e.printStackTrace();
                new CustomToast(this, "Failed to export GOCas Info. Please contact MIS.", CustomToast.TYPE.WARNING).show();
            }
        });

        /*adapter.setOnApplicationClickListener((position, TransNox) -> {
            GOCASApplication loGoCas = new CreditApplication(Activity_ApplicationLog.this).getGOCasInfoForCI(TransNox);
            String lsGoCas = loGoCas.toJSONString();
            Intent loIntent = new Intent(Activity_ApplicationLog.this, Activity_InformationCI.class);
            loIntent.putExtra("gocas", lsGoCas);
            startActivity(loIntent);
        });*/
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
