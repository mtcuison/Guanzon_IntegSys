package org.rmj.guanzongroup.promotions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Dialog.MessageDialog;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.guanzongroup.promotions.Etc.RaffleEntryCallback;
import org.rmj.guanzongroup.promotions.Model.TownInfo;
import org.rmj.guanzongroup.promotions.Model.Voucher;
import org.rmj.guanzongroup.promotions.ViewModel.PromoViewModel;

import java.util.List;

public class Activity_RaffleEntry extends AppCompatActivity implements RaffleEntryCallback {
    public static final String TAG = Activity_RaffleEntry.class.getSimpleName();

    private PromoViewModel mViewModel;

    private TextInputEditText txtName, txtAddress, txtDocuNo, txtMobileNo;
    private AutoCompleteTextView txtTown;
    private Spinner spnDocuType;
    private String lsTownIDx = "";
    private String lsProvIDx = "";
    private ProgressDialog poDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raffle_entry);
        poDialog = new ProgressDialog(Activity_RaffleEntry.this);
        mViewModel = ViewModelProviders.of(Activity_RaffleEntry.this).get(PromoViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar_vouchers);
        txtName = findViewById(R.id.txt_customerName);
        txtAddress = findViewById(R.id.txt_customerAdd);
        txtTown = findViewById(R.id.txt_customerTown);
        txtDocuNo = findViewById(R.id.txt_documentNumber);
        txtMobileNo = findViewById(R.id.txt_mobileNo);
        spnDocuType = findViewById(R.id.spn_documentType);
        MaterialButton btnSubmit = findViewById(R.id.btn_promoSubmit);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lsCustomerNm = txtName.getText().toString();
                String lsCustomerAd = txtAddress.getText().toString();
                String lsDocumentNo = txtDocuNo.getText().toString();
                String lsMobileNoxx = txtMobileNo.getText().toString();
                String lsBranchCode = AppData.getInstance(Activity_RaffleEntry.this).getBranchCode();
                Voucher voucher = new Voucher();
                voucher.setCustomerName(lsCustomerNm);
                voucher.setCustomerAddx(lsCustomerAd);
                voucher.setDocumentNoxx(lsDocumentNo);
                voucher.setMobileNumber(lsMobileNoxx);
                voucher.setBranchCodexx(lsBranchCode);
                voucher.setCustomerTown(lsTownIDx);
                voucher.setCustomerProv(lsProvIDx);
                mViewModel.submit(voucher, Activity_RaffleEntry.this);
            }
        });

        mViewModel.getTownInfos().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_RaffleEntry.this, R.layout.spinner_drop_down_item, strings);
                txtTown.setAdapter(adapter);
            }
        });

        txtTown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mViewModel.getTownInfoList().observe(Activity_RaffleEntry.this, new Observer<List<TownInfo>>() {
                    @Override
                    public void onChanged(List<TownInfo> townInfos) {
                        for(int x = 0; x < townInfos.size(); x++){
                            String lsTownInfo = townInfos.get(x).getsTownNm() + ", " +
                                    townInfos.get(x).getsProvNm() + ", " +
                                    townInfos.get(x).getsZippCd();
                            if(txtTown.getText().toString().equalsIgnoreCase(lsTownInfo)){
                                lsTownIDx = townInfos.get(x).getsTownID();
                                lsProvIDx = townInfos.get(x).getsProvID();
                                break;
                            }
                        }
                    }
                });
            }
        });

        mViewModel.getDocuments().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                spnDocuType.setAdapter(new ArrayAdapter<>(Activity_RaffleEntry.this, R.layout.spinner_drop_down_item, strings));
                spnDocuType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        mViewModel.setDocumentPosition(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void OnSendingEntry() {
        poDialog.setCancelable(false);
        poDialog.setTitle("Raffle Entry");
        poDialog.setMessage("Uploading entry. Please wait...");
        poDialog.show();
    }

    @Override
    public void OnSuccessEntry() {
        if(poDialog.isShowing()) {
            poDialog.dismiss();
        }
        txtName.setText("");
        txtAddress.setText("");
        lsTownIDx = "";
        lsProvIDx = "";
        txtTown.setText("");
        txtDocuNo.setText("");
        txtMobileNo.setText("");
        spnDocuType.setSelection(0);

        MessageDialog loDialog = new MessageDialog(Activity_RaffleEntry.this);
        loDialog.setMessage("Raffle Entry");
        loDialog.setMessage("Information has been sent to server. Please inform the customer to wait for SMS with link attachment");
        loDialog.setPositiveButton("Okay", new MessageDialog.onMessageDialogButtonClickListener() {
            @Override
            public void onButtonClick(android.app.AlertDialog dialog) {
                dialog.dismiss();
            }
        });
        loDialog.showDialog();
    }

    @Override
    public void OnFailedEntry(String message) {
        if(poDialog.isShowing()) {
            poDialog.dismiss();
        }
        txtName.setText("");
        txtAddress.setText("");
        lsTownIDx = "";
        lsProvIDx = "";
        txtTown.setText("");
        txtDocuNo.setText("");
        txtMobileNo.setText("");
        spnDocuType.setSelection(0);

        MessageDialog loDialog = new MessageDialog(Activity_RaffleEntry.this);
        loDialog.setMessage("Raffle Entry");
        loDialog.setMessage(message);
        loDialog.setPositiveButton("Okay", new MessageDialog.onMessageDialogButtonClickListener() {
            @Override
            public void onButtonClick(android.app.AlertDialog dialog) {
                dialog.dismiss();
            }
        });
        loDialog.showDialog();
    }
}