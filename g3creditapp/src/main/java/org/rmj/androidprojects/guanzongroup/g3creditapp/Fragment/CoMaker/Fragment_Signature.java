package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.CoMaker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.gcacace.signaturepad.views.SignaturePad;

import org.rmj.androidprojects.guanzongroup.g3creditapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Signature extends Fragment {
    private static final String TAG = Fragment_Signature.class.getSimpleName();

    private SignaturePad signPad;

    public Fragment_Signature() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_signature, container, false);

        signPad = view.findViewById(R.id.signature_pad);

        Button btnClear = view.findViewById(R.id.btnSignClear);
        Button btnSave = view.findViewById(R.id.btnSignSave);

        btnClear.setOnClickListener(new OnButtonClickListener());
        btnSave.setOnClickListener(new OnButtonClickListener());
        signPad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                btnClear.setEnabled(true);
                btnSave.setEnabled(true);
            }

            @Override
            public void onClear() {
                btnClear.setEnabled(false);
                btnSave.setEnabled(false);
            }
        });

        return view;
    }

    class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.btnSignClear) {
                signPad.clear();
            } else if (id == R.id.btnSignSave) {
            }
        }
    }
}
