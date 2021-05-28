package org.rmj.guanzongroup.bullseye.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.rmj.guanzongroup.bullseye.Adapter.Branch_Adapter;
import org.rmj.guanzongroup.bullseye.LocalData.Area_Model;
import org.rmj.guanzongroup.bullseye.LocalData.Branch_Model;
import org.rmj.guanzongroup.bullseye.LocalData.BullseyeLocalData;
import org.rmj.guanzongroup.bullseye.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_Branch extends AppCompatActivity {
    private static final String TAG = Activity_Branch.class.getSimpleName();

    private String AreaCode;
    private RecyclerView recyclerView;
    private TextView lblArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        Toolbar toolbar = findViewById(R.id.toolbar_bullseye_branch);
        toolbar.setTitle("Branch Performance");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview_branchlist);
        lblArea = findViewById(R.id.lbl_header_branchMonitoring);
        setupBarList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBarList(){
        lblArea.setText("Branch Monitoring of Area " + getIntent().getStringExtra("areanm"));
        AreaCode = getIntent().getStringExtra("areacd");
        List<Branch_Model> branchModels = new BullseyeLocalData(Activity_Branch.this).getBranchList(AreaCode);
        int DynamicSize = new BullseyeLocalData(Activity_Branch.this).getBranchDynamicBarSize();
        Branch_Adapter adapter = new Branch_Adapter(branchModels, DynamicSize);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_Branch.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setupBarChart(){
        BarChart barChart = findViewById(R.id.barchar_branch_performance);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> entryNames = new ArrayList<>();
        AreaCode = getIntent().getStringExtra("areacd");
        List<Branch_Model> branchModels = new BullseyeLocalData(Activity_Branch.this).getBranchList(AreaCode);
        for(int x = 0; x < branchModels.size(); x++){
            String branchName = branchModels.get(x).getBranchName();
            float percentage = Float.parseFloat(branchModels.get(x).getSalesPercentage());
            barEntries.add(new BarEntry(x, percentage));
            entryNames.add(branchName);
        }

        BarDataSet dataSet = new BarDataSet(barEntries, "Area Monitoring");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Area");
        barChart.setDescription(description);
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
    }
}
