package org.rmj.guanzongroup.integsys.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.ImportInstance;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_BarangayAsset;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_Brand;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_Category;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_CountryAsset;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_Occupation;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_ProvinceAsset;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_Religion;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_TermCategoryAsset;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_TownAsset;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_Branches;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_McModel;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.Import_McModel_Price;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate.OnImportAssetListener;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.guanzongroup.integsys.R;
import org.rmj.guanzongroup.promotions.Local.Import_RaffleBasis;

public class Activity_LoadAsset extends AppCompatActivity {
    private static final String TAG = Activity_LoadAsset.class.getSimpleName();

    private ProgressBar progressBar;
    private ImportInstance[] instances = {
            new Import_BarangayAsset(),
            new Import_TownAsset(),
            new Import_ProvinceAsset(),
            new Import_CountryAsset(),
            new Import_McModel(),
            new Import_McModel_Price(),
            new Import_Brand(),
            new Import_Category(),
            new Import_TermCategoryAsset(),
            new Import_Religion(),
            new Import_Branches(),
            new Import_Occupation(),
            new Import_RaffleBasis()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_asset);
        progressBar = findViewById(R.id.progressbar_loadAsset);
        //load(this);
        DownloadBackgroundAsset loAssetDownload = new DownloadBackgroundAsset();
        loAssetDownload.execute(instances.length);
        loAssetDownload.setLoListener(message -> {
            Log.e(TAG, message);
            loAssetDownload.cancel(true);
        });
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadBackgroundAsset extends AsyncTask<Integer, Integer, String>{

        OnBackgroundDownloadFinishedListener loListener;

        void setLoListener(OnBackgroundDownloadFinishedListener listener){
            this.loListener = listener;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for(int x =0; x < integers[0]; x ++){
                instances[x].SendImportRequest(Activity_LoadAsset.this, new OnImportAssetListener() {
                    @Override
                    public void onImportSuccess() {

                    }

                    @Override
                    public void onImportFailed(String Message) {

                    }
                });

                Log.e(TAG, "Background counting. counter : " + x);
                publishProgress((int) ((x / (float) integers[0]) * 100));
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            loListener.OnFinished("Assets has been successfully downloaded.");
            new SessionManager(Activity_LoadAsset.this).setIsFirstLaunched(false);
            startActivity(new Intent(Activity_LoadAsset.this, MainActivity.class));
            finish();
            return "Finished!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
    }

    interface OnBackgroundDownloadFinishedListener{
        void OnFinished(String message);
    }
}
