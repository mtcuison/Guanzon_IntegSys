package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.CoMaker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

import java.io.File;
import java.util.Objects;

import static org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.CameraCaptureType.Bill_Rcpt;
import static org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.CameraCaptureType.Photo_2x2;
import static org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.CameraCaptureType.Valid_ID1;
import static org.rmj.androidprojects.guanzongroup.g3creditapp.Etc.CameraCaptureType.Valid_ID2;

/**
 * A simple {@link Fragment} subclass.
 */

public class Fragment_Requirements extends Fragment {
    @SuppressLint("StaticFieldLeak")
    private static Fragment_Requirements instance;
    private View v;

    private ImageView appl2x2Photo;
    private ImageView applValidID1;
    private ImageView applValidID2;
    private ImageView applBillRcpt;

    private String currentPhotoPath;

    public Fragment_Requirements() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        instance = this;
        v = inflater.inflate(R.layout.fragment_requirements, container, false);

        setupWidgets();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            fetchImageFromStorage();
            setImgeViewBitmap(requestCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setupWidgets(){
        appl2x2Photo = v.findViewById(R.id.img_creditApp_2x2applicantImage);
        applValidID1 = v.findViewById(R.id.img_creditApp_1stValidID);
        applValidID2 = v.findViewById(R.id.img_creditApp_2ndValidID);
        applBillRcpt = v.findViewById(R.id.img_creditApp_billsReceipt);

        MaterialButton btnCaptur2x2Photo = v.findViewById(R.id.btn_creditApp_applicant_takePhoto);
        MaterialButton btnSelectPhoto = v.findViewById(R.id.btn_creditApp_applicant_uploadPhoto);
        MaterialButton btnChooseValidID1 = v.findViewById(R.id.btn_creditApp_applicant_1stValidID);
        MaterialButton btnChooseValidID2 = v.findViewById(R.id.btn_creditApp_applicant_2ndValidID);
        MaterialButton btnCaptreBillRcpt = v.findViewById(R.id.btn_creditApp_applicant_paperBills);

        MaterialButton btnReviewAppl = v.findViewById(R.id.btn_fragment_rqrmnts_prevs);
        MaterialButton btnSubmitAppl = v.findViewById(R.id.btn_fragment_rqrmnts_next);

        btnCaptur2x2Photo.setOnClickListener(v -> capturePhoto(Photo_2x2));

        btnSelectPhoto.setOnClickListener(v -> {

        });

        btnChooseValidID1.setOnClickListener(v -> capturePhoto(Valid_ID1));

        btnChooseValidID2.setOnClickListener(v -> capturePhoto(Valid_ID2));

        btnCaptreBillRcpt.setOnClickListener(v -> capturePhoto(Bill_Rcpt));

        btnReviewAppl.setOnClickListener(v -> ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(4, true));
    }

    @SuppressLint("NewApi")
    private void capturePhoto(int CaptureType){
        Intent sendCameraAction = new Intent("org.rmj.Camera_Instance");
        sendCameraAction.putExtra("capturetype", CaptureType);
        Objects.requireNonNull(getActivity()).sendBroadcast(sendCameraAction);
    }

    @SuppressLint("NewApi")
    private void fetchImageFromStorage(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        Objects.requireNonNull(getActivity()).sendBroadcast(mediaScanIntent);
    }

    private void setImgeViewBitmap(int CaptureType){
        // Get the dimensions of the View
        int targetW = getViewHeight(CaptureType);
        int targetH = getViewWidth(CaptureType);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        CaptureView(CaptureType).setImageBitmap(bitmap);
    }

    public Fragment_Requirements getInstance(){
        return instance;
    }

    public void openCamera(Intent cameraIntent, int CaptureType, String currentPhotoPath){
        this.currentPhotoPath = currentPhotoPath;
        startActivityForResult(cameraIntent, CaptureType);
    }

    private int getViewHeight(int CaptureType){
        switch (CaptureType){
            case Photo_2x2:
                return appl2x2Photo.getHeight();
            case Valid_ID1:
                return applValidID1.getHeight();
            case Valid_ID2:
                return applValidID2.getHeight();
            case Bill_Rcpt:
                return applBillRcpt.getHeight();
            default:
                throw new IllegalStateException("Unexpected value: " + CaptureType);
        }
    }

    private int getViewWidth(int CaptureType){
        switch (CaptureType){
            case Photo_2x2:
                return appl2x2Photo.getWidth();
            case Valid_ID1:
                return applValidID1.getWidth();
            case Valid_ID2:
                return applValidID2.getWidth();
            case Bill_Rcpt:
                return applBillRcpt.getWidth();
            default:
                throw new IllegalStateException("Unexpected value: " + CaptureType);
        }
    }

    private ImageView CaptureView(int CaptureType){
        switch (CaptureType){
            case Photo_2x2:
                return appl2x2Photo;
            case Valid_ID1:
                return applValidID1;
            case Valid_ID2:
                return applValidID2;
            case Bill_Rcpt:
                return applBillRcpt;
            default:
                throw new IllegalStateException("Unexpected value: " + CaptureType);
        }
    }
}
