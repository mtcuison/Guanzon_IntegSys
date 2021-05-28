package org.rmj.g3cm.android.g3cashmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3cm.android.g3cashmanager.LocalData.CashCountLocalData;
import org.rmj.g3cm.android.g3cashmanager.kwiksearch.adapter.Adapter_CashCount;
import org.rmj.g3cm.android.g3cashmanager.kwiksearch.adapter.CashCountLog;

import java.util.ArrayList;
import java.util.List;

public class Activity_CashCountLog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_count_log);

        Toolbar toolbar = findViewById(R.id.toolbar_cashCountLog);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_cashcountLog);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_CashCountLog.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(getRecyclerViewAdapter());
        recyclerView.setLayoutManager(layoutManager);
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

    private Adapter_CashCount getRecyclerViewAdapter(){
        List<CashCountLog> cashCountLogList = new ArrayList<>();
        AppData db = AppData.getInstance(Activity_CashCountLog.this);
        Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM Cash_Count_Master", null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            for(int x = 0; x < cursor.getCount(); x++){
                CashCountLog cashCountLog = new CashCountLog(
                        cursor.getString(cursor.getColumnIndex("sTransNox")),
                                cursor.getString(cursor.getColumnIndex("dTransact")),
                        cursor.getString(cursor.getColumnIndex("sReqstdBy")),
                        cursor.getString(cursor.getColumnIndex("dReceived")));
                cashCountLogList.add(cashCountLog);
                cursor.moveToNext();
            }
            cursor.close();
            return new Adapter_CashCount(cashCountLogList);
        }
        cursor.close();
        return null;
    }
}
