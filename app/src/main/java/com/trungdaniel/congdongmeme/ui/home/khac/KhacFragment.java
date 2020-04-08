package com.trungdaniel.congdongmeme.ui.home.khac;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;
import com.trungdaniel.congdongmeme.R;
import com.trungdaniel.congdongmeme.ui.home.Anh;
import com.trungdaniel.congdongmeme.ui.home.hoathinh.HoatHinhAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class KhacFragment extends Fragment {
    private RecyclerView rvKhac;
    private RecyclerView.LayoutManager layoutManager;
    private KhacAdapter khacAdapter;

    public KhacFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_khac, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getKey();
    }

    private void getKey() {
        DatabaseReference referenceKey = FirebaseDatabase.getInstance().getReference().child("uploads").child("khác");
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
        DatabaseReference referenceData = FirebaseDatabase.getInstance().getReference().child("uploads").child("khác").child(key);
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
                    khacAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init(View view) {
        rvKhac = view.findViewById(R.id.rv_khac);
        layoutManager = new GridLayoutManager(getContext(), 3);
        rvKhac.setLayoutManager(layoutManager);
        rvKhac.addItemDecoration(new LayoutMarginDecoration(3, 10));
        khacAdapter = new KhacAdapter(getContext(), getAnhNguoi());
        rvKhac.setAdapter(khacAdapter);

    }

    private ArrayList<Anh> anhArrayList = new ArrayList<>();

    private List<Anh> getAnhNguoi() {
        return anhArrayList;
    }

}
