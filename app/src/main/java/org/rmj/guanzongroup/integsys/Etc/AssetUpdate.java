package org.rmj.guanzongroup.integsys.Etc;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.rmj.g3appdriver.dev.AppData;

public class AssetUpdate {
    private static final String TAG = AssetUpdate.class.getSimpleName();

    private Context mContext;

    public AssetUpdate(Context context){
        this.mContext = context;
    }

    public boolean isAssetUpdated(){
        AppData db = AppData.getInstance(mContext);
        Cursor cursor = db.getReadableDb().rawQuery("SELECT SUBSTR(dTimeStmp, 0, 11) FROM CreditApp_Barangay WHERE SUBSTR(dTimeStmp, 0, 11) = DATE('now')", null);
        if(cursor.getCount()>0){
            db.close();
            cursor.close();
            Log.e(TAG, "Asset update check result : UPDATED");
            return true;
        }
        db.close();
        cursor.close();
        Log.e(TAG, "Asset update check result : OUTDATED");
        return false;
    }
}
