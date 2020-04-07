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
    private RecyclerView rvDongVat;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnTaiConVat, btnShowConVat;
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
        taiAnhConVat();
    }

    private void taiAnhConVat() {
        conVatAdapter = new ConVatAdapter(getContext(), getData());
        layoutManager = new GridLayoutManager(getContext(), 3);
        rvDongVat.setLayoutManager(layoutManager);
        rvDongVat.addItemDecoration(new LayoutMarginDecoration(3, 10));
        rvDongVat.setAdapter(conVatAdapter);
        if (conVatAdapter.getItemCount() == 0) {
            btnShowConVat.setVisibility(View.INVISIBLE);
            btnTaiConVat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    for (int i = 0; i < anhArrayList.size(); i++) {
                        file_download(anhArrayList.get(i).getUriImage());
                        if (i == anhArrayList.size() - 1) {
                            Toast.makeText(getActivity(), "Tải ảnh thành công. vui lòng nhấn nút hiển thị", Toast.LENGTH_SHORT).show();
                            btnShowConVat.setVisibility(View.VISIBLE);
                        }
                    }
                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Vui lòng đợi ....");
                    dialog.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 1500);
                    btnTaiConVat.setVisibility(View.INVISIBLE);
                    btnShowConVat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            conVatAdapter = new ConVatAdapter(getContext(), getData());
                            layoutManager = new GridLayoutManager(getContext(), 3);
                            rvDongVat.setLayoutManager(layoutManager);
                            rvDongVat.addItemDecoration(new LayoutMarginDecoration(3, 10));
                            rvDongVat.setAdapter(conVatAdapter);
                            btnShowConVat.setVisibility(View.INVISIBLE);
                        }
                    });


                }
            });
        } else {
            btnTaiConVat.setVisibility(View.INVISIBLE);
            btnShowConVat.setVisibility(View.INVISIBLE);
        }


    }

    private ArrayList<Saved> getData() {
        ArrayList<Saved> savedArrayList = new ArrayList<>();
        //TARGET FOLDER
        File downloadsFolder = Environment.getExternalStoragePublicDirectory("MemeDongVat");

        Saved s;

        if (downloadsFolder.exists()) {
            //GET ALL FILES IN DOWNLOAD FOLDER
            File[] files = downloadsFolder.listFiles();

            //LOOP THRU THOSE FILES GETTING NAME AND URI
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                s = new Saved();
                s.setUri(Uri.fromFile(file));
                savedArrayList.add(s);
            }
        }

        return savedArrayList;
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

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void file_download(String uRl) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/MemeDongVat");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir("/MemeDongVat", "test.jpg");

        mgr.enqueue(request);

    }


    private void init(View view) {
        rvDongVat = view.findViewById(R.id.rv_con_vat);
        btnTaiConVat = view.findViewById(R.id.btn_tai_con_vat);
        btnShowConVat = view.findViewById(R.id.btn_show_con_vat);

    }

    private List<Anh> anhArrayList = new ArrayList<>();
}
