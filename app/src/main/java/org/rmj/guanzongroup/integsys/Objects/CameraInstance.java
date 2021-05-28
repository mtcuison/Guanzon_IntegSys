package org.rmj.guanzongroup.integsys.Objects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraInstance {
    private Context mContext;

    private String currentPhotoPath;

    public CameraInstance(Context context){
        this.mContext = context;
    }

    public void openCamera(int CaptureType, onCreateCameraInstance listener){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(mContext.getPackageManager())!=null){
            File photoFile = null;

            try{
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(mContext, "org.rmj.guanzongroup.integsys.provider", photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                listener.onCameraInstanceCreated(cameraIntent, CaptureType, currentPhotoPath);
            }
        }
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
        String imageFilename = " CreditApplicant_ " + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFilename,  /*prefix*/
                ".jpg",  /*suffix*/
                storageDir      /*directory*/
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public interface onCreateCameraInstance{
        void onCameraInstanceCreated(Intent cameraIntent, int CaptureType, String currentPhotoPath);
    }
}
