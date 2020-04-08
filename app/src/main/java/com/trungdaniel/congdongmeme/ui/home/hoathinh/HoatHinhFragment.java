package com.trungdaniel.congdongmeme.ui.home.hoathinh;


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
import com.trungdaniel.congdongmeme.ui.home.convat.ConVatAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HoatHinhFragment extends Fragment {
    private RecyclerView rvHoatHinh;
    private RecyclerView.LayoutManager layoutManager;
    HoatHinhAdapter hoatHinhAdapter;


    public HoatHinhFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoat_hinh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getKey();
    }

    private void getKey() {
        DatabaseReference referenceKey = FirebaseDatabase.getInstance().getReference().child("uploads").child("Hoạt hình");
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
        DatabaseReference referenceData = FirebaseDatabase.getInstance().getReference().child("uploads").child("Hoạt hình").child(key);
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
                    hoatHinhAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init(View view) {
        rvHoatHinh = view.findViewById(R.id.rv_hoat_hinh);
        layoutManager = new GridLayoutManager(getContext(), 3);
        rvHoatHinh.setLayoutManager(layoutManager);
        rvHoatHinh.addItemDecoration(new LayoutMarginDecoration(3, 10));
        hoatHinhAdapter = new HoatHinhAdapter(getContext(), getAnhNguoi());
        rvHoatHinh.setAdapter(hoatHinhAdapter);

    }

    private ArrayList<Anh> anhArrayList = new ArrayList<>();

    private List<Anh> getAnhNguoi() {
        return anhArrayList;
    }

}
