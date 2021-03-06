package com.trungdaniel.congdongmeme.ui.home.convat;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;
import com.trungdaniel.congdongmeme.MainActivity;
import com.trungdaniel.congdongmeme.R;
import com.trungdaniel.congdongmeme.ui.home.Anh;
import com.trungdaniel.congdongmeme.ui.home.nguoi.NguoiAdapter;
import com.trungdaniel.congdongmeme.ui.saved.Saved;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConVatFragment extends Fragment {
    private RecyclerView rvConVat;
    private RecyclerView.LayoutManager layoutManager;
    ConVatAdapter conVatAdapter;

    public ConVatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_con_vat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getKey();
    }

    private void getKey() {
        DatabaseReference referenceKey = FirebaseDatabase.getInstance().getReference().child("uploads").child("Con vật");
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
        DatabaseReference referenceData = FirebaseDatabase.getInstance().getReference().child("uploads").child("Con vật").child(key);
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
                    conVatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init(View view) {
        rvConVat = view.findViewById(R.id.rv_con_vat);
        layoutManager = new GridLayoutManager(getContext(), 3);
        rvConVat.setLayoutManager(layoutManager);
        rvConVat.addItemDecoration(new LayoutMarginDecoration(3, 10));
        conVatAdapter = new ConVatAdapter(getContext(), getAnhNguoi());
        rvConVat.setAdapter(conVatAdapter);

    }

    private ArrayList<Anh> anhArrayList = new ArrayList<>();

    private List<Anh> getAnhNguoi() {
        return anhArrayList;
    }


}
