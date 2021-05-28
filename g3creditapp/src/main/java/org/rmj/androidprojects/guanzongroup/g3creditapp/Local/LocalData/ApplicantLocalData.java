package org.rmj.androidprojects.guanzongroup.g3creditapp.Local.LocalData;

import android.content.Context;
import android.database.Cursor;

import org.json.JSONObject;
import org.rmj.androidprojects.guanzongroup.g3creditapp.CreditAppObjects.ApplicationLog;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.gocas.base.GOCASApplication;

import java.util.ArrayList;
import java.util.List;

public class ApplicantLocalData {
    private static final String TAG = ApplicantLocalData.class.getSimpleName();

    private Context mContext;
    private AppData db;

    public ApplicantLocalData(Context context){
        this.mContext = context;
        this.db = AppData.getInstance(mContext);
    }

    public List<ApplicationLog> getApplicationLogList(){
        try{
            List<ApplicationLog> applicationLogList = new ArrayList<>();
            String[] bindArgs = {getUserID()};
            Cursor cursor = db.getReadableDb().rawQuery("Select a.*, b.sBranchNm " +
                    "From Credit_Online_Application a " +
                    "Left Join CreditApp_Branches b " +
                    "ON a.sBranchCd = b.sBranchCd " +
                    "WHERE cTranStat != 4 " +
                    "AND sCreatedx = ? " +
                    "ORDER BY a.dCreatedx DESC", bindArgs);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                for(int x = 0; x < cursor.getCount(); x++){
                    String applJson = cursor.getString(cursor.getColumnIndex("sDetlInfo"));
                    ApplicationLog applicationLog = new ApplicationLog(mContext,
                            cursor.getString(cursor.getColumnIndex("sGOCASNox")),
                            cursor.getString(cursor.getColumnIndex("sTransNox")),
                            cursor.getString(cursor.getColumnIndex("sBranchNm")),
                            cursor.getString(cursor.getColumnIndex("dCreatedx")),
                            applJson,
                            cursor.getString(cursor.getColumnIndex("sClientNm")),
                            getModelID(cursor.getString(cursor.getColumnIndex("sDetlInfo"))),
                            parseJsonToString(applJson, "nDownPaym"),
                            "",
                            cursor.getString(cursor.getColumnIndex("cWithCIxx")),
                            parseJsonToString(applJson, "cUnitAppl"),
                            cursor.getString(cursor.getColumnIndex("cSendStat")),
                            cursor.getString(cursor.getColumnIndex("cTranStat")),
                            cursor.getString(cursor.getColumnIndex("dReceived")),
                            cursor.getString(cursor.getColumnIndex("dVerified")));
                    applicationLogList.add(applicationLog);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            return applicationLogList;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String parseJsonToString(String JsonObject, String KeyValue){
        try{
            GOCASApplication loGoCas = new GOCASApplication();
            loGoCas.setData(JsonObject);
            JSONObject params = new JSONObject(loGoCas.toJSONString());
            return params.getString(KeyValue);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String getModelID(String jsonData){
        GOCASApplication loGoCas = new GOCASApplication();
        loGoCas.setData(jsonData);
        return loGoCas.PurchaseInfo().getModelID();
    }

    private String getUserID(){
        Cursor cursor = db.getReadableDb().rawQuery("SELECT * FROM User_Info_Master", null);
        cursor.moveToFirst();
        String UserID = cursor.getString(cursor.getColumnIndex("sUserIDxx"));
        cursor.close();
        return UserID;
    }
}
