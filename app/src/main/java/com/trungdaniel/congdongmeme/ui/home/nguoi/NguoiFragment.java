package com.trungdaniel.congdongmeme.ui.home.nguoi;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trungdaniel.congdongmeme.R;
import com.trungdaniel.congdongmeme.ui.home.Anh;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NguoiFragment extends Fragment {
    private RecyclerView rvNguoi;
    private RecyclerView.LayoutManager layoutManager;
    NguoiAdapter nguoiAdapter;

    public NguoiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nguoi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getKey();

    }

    private void getKey() {
        DatabaseReference referenceKey = FirebaseDatabase.getInstance().getReference().child("uploads").child("Người");
        referenceKey.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot keyList : dataSnapshot.getChildren()) {
                        getData(keyList.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getData(String key) {
        DatabaseReference referenceData = FirebaseDatabase.getInstance().getReference().child("uploads").child("Người").child(key);
        referenceData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String uriAnh = "";
                    if (dataSnapshot.child("uriImage").getValue() != null) {
                        uriAnh = dataSnapshot.child("uriImage").getValue().toString();
                    }
                    Anh anh = new Anh(uriAnh);
                    anhArrayList.add(anh);
                    nguoiAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void init(View view) {
        rvNguoi = view.findViewById(R.id.rv_nguoi);
        layoutManager = new GridLayoutManager(getContext(), 4);
        rvNguoi.setLayoutManager(layoutManager);
        nguoiAdapter = new NguoiAdapter(getContext(), getAnhNguoi());
        rvNguoi.setAdapter(nguoiAdapter);

    }

    private ArrayList<Anh> anhArrayList = new ArrayList<>();

    private List<Anh> getAnhNguoi() {
        return anhArrayList;
    }


}
