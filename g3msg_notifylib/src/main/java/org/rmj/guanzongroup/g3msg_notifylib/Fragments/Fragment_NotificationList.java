package org.rmj.guanzongroup.g3msg_notifylib.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.g3appdriver.dev.AppData;
import org.rmj.guanzongroup.g3msg_notifylib.Activities.Activity_NotificationView;
import org.rmj.guanzongroup.g3msg_notifylib.Adapter.g3NotificationAdapter;
import org.rmj.guanzongroup.g3msg_notifylib.DbHelper.Get_NotificationData;
import org.rmj.guanzongroup.g3msg_notifylib.Model.NotificationMessageModel;
import org.rmj.guanzongroup.g3msg_notifylib.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_NotificationList extends Fragment {
    private static final String TAG = Fragment_NotificationList.class.getSimpleName();
    private static Fragment_NotificationList instance;
    private View view;
    private g3NotificationAdapter loAdapter;

    public Fragment_NotificationList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        instance = this;
        view = inflater.inflate(R.layout.fragment_notification_list, container, false);
        setupList();
        return view;
    }

    private void setupList(){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_notificationList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        String lsUserId = AppData.getInstance(getActivity()).getUserID();
        final List<NotificationMessageModel> loNotifications = new Get_NotificationData().getEmployeeMessages(getActivity(), lsUserId);
        loAdapter = new g3NotificationAdapter(loNotifications);
        recyclerView.setAdapter(loAdapter);
        recyclerView.setLayoutManager(layoutManager);
        loAdapter.setOnNotificationClickListener(new g3NotificationAdapter.OnNotificationClickListener() {
            @Override
            public void OnClick(int position, boolean isRead) {
                Intent intent = new Intent(getActivity(), Activity_NotificationView.class);
                intent.putExtra("notification", loNotifications.get(position));
                startActivity(intent);
            }
        });
    }

    public static Fragment_NotificationList getInstance(){
        return instance;
    }

    public void refreshView(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setupList();
            }
        });
    }
}
