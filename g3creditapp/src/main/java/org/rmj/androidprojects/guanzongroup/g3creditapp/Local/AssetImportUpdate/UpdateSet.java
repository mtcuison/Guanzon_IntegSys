package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.AssetImportUpdate;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.etc.SessionManager;

class UpdateSet {
    private static final String TAG = UpdateSet.class.getSimpleName();

    private Context mContext;

    UpdateSet(Context context){
        this.mContext = context;
    }

    JSONObject getDataParameter(String TableName) throws JSONException {
        JSONObject loJson = new JSONObject();
        String lsTimeStmp = getTimeStamp(TableName);
        if(new SessionManager(mContext).isFirstLaunch()){
            loJson.put("bsearch", true);
            loJson.put("descript", "All");
        } else {
            loJson.put("bsearch", true);
            loJson.put("descript", "All");
            loJson.put("dTimeStmp", lsTimeStmp);
        }
        return loJson;
    }

    private String getTimeStamp(String TableName){
        String lsTimeStamp = "";
        Log.e(TAG, "Time Stamp Table : " + TableName);
        AppData loDb = AppData.getInstance(mContext);
        Cursor loCursor = loDb.getReadableDb().rawQuery("SELECT dTimeStmp FROM '"+TableName+"' ORDER BY dTimeStmp DESC", null);
        if(loCursor.getCount() > 0){
             loCursor.moveToFirst();
             lsTimeStamp = loCursor.getString(0);
             loCursor.close();
        }
        Log.e(TAG,"Current local time stamp : " + lsTimeStamp);
        return lsTimeStamp;
    }

    public interface LocalTables{
        String Barangay = "CreditApp_Barangay";
        String Model = "Mc_Model";
        String Brand = "MC_Brand";
        String Category = "MC_Category";
        String Country = "CreditApp_Country";
        String Price = "Mc_Model_Price";
        String Occupation = "CreditApp_Occupations";
        String Province = "CreditApp_Province";
        String Religion = "CreditApp_Religion";
        String Term = "MC_Term_Category";
        String Town = "CreditApp_Town";
    }
}
