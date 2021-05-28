package org.rmj.androidprojects.guanzongroup.g3creditapp.Fragment.Spouse;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.androidprojects.guanzongroup.g3creditapp.Activity.Activity_CreditApplication;
import org.rmj.androidprojects.guanzongroup.g3creditapp.Adapter.FragmentAdapter;
import org.rmj.androidprojects.guanzongroup.g3creditapp.R;
import org.rmj.gocas.base.GOCASApplication;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_SpouseMeans extends Fragment {
    private static final String TAG = Fragment_SpouseMeans.class.getSimpleName();
    private static Fragment_SpouseMeans instance;
    private GOCASApplication loGoCas;
    private FragmentAdapter adapter;
    private ViewPager viewPager;

    public static Fragment_SpouseMeans getInstance(){
        return instance;
    }

    public int getFragmentSize(){
        return adapter.getCount();
    }

    public Fragment_SpouseMeans() {
        // Required empty public constructor
    }

    public void setFragments(ArrayList<Fragment> fragments){
        for(int x = 0; x < fragments.size(); x++){
            adapter.addFragment(fragments.get(x));
        }
        adapter.notifyDataSetChanged();
        Log.e(TAG, "Income form adapter has been updated.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        instance = this;
        View v = inflater.inflate(R.layout.fragment_spouse_means, container, false);

        viewPager = v.findViewById(R.id.viewpager_spsEmploymentInfo);

        adapter = new FragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        return v;
    }

    void goNextFragment(){
        if(adapter.getCount() > 1){
            int liCurrentItem = viewPager.getCurrentItem() + 1;
            int liAdapterCount = adapter.getCount();
            if(liCurrentItem < liAdapterCount) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                goToNextPage();
            }
        } else {
            goToNextPage();
        }
    }

    void goPreviousFragment(){
        if(adapter.getCount() > 1){
            if(viewPager.getCurrentItem() - 1 >= 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            } else {
                ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(4, true);
            }
        } else {
            ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(4, true);
        }
    }

    private void goToNextPage(){
        ((Activity_CreditApplication) Objects.requireNonNull(getActivity())).setCurrentItem(6, true);
    }

}
