package org.rmj.guanzongroup.integsys.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Fragments extends FragmentStatePagerAdapter {

    private List<Fragment> mFragment_List = new ArrayList<>();

    public Adapter_Fragments(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragment_List.get(position);
    }

    @Override
    public int getCount() {
        return mFragment_List.size();
    }

    public void addFragment(Fragment fragment){
        mFragment_List.add(fragment);
    }
}
