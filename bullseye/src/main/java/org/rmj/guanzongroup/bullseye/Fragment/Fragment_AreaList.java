package org.rmj.guanzongroup.bullseye.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.rmj.guanzongroup.bullseye.Activities.Activity_Branch;
import org.rmj.guanzongroup.bullseye.Adapter.Area_Adapter;
import org.rmj.guanzongroup.bullseye.LocalData.BullseyeLocalData;
import org.rmj.guanzongroup.bullseye.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_AreaList extends Fragment {
    private static final String TAG = Fragment_AreaList.class.getSimpleName();
    private View view;
    public Fragment_AreaList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_area_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_arealist);

        Area_Adapter adapter = new Area_Adapter(new BullseyeLocalData(getActivity()).getAreaList());
        adapter.setOnBarGraphClickListener(new Area_Adapter.OnBarGraphClickListener() {
            @Override
            public void OnClick(String AreaCode, String AreaName) {
                Intent intent = new Intent(getActivity(), Activity_Branch.class);
                intent.putExtra("areacd", AreaCode);
                intent.putExtra("areanm", AreaName);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        return view;
    }
}
