package org.rmj.guanzongroup.integsys.Fragment.Collection;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.integsys.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_CollectionNonPriority extends Fragment {


    public Fragment_CollectionNonPriority() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection_non_priority, container, false);
    }

}
