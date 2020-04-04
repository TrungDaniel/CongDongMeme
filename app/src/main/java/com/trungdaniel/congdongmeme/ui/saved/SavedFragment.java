package com.trungdaniel.congdongmeme.ui.saved;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;
import com.trungdaniel.congdongmeme.R;

import java.io.File;
import java.util.ArrayList;

public class SavedFragment extends Fragment {
    private RecyclerView rvSaved;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private ArrayList<Saved> getData() {
        ArrayList<Saved> savedArrayList = new ArrayList<>();
        //TARGET FOLDER
        File downloadsFolder = Environment.getExternalStoragePublicDirectory("Meme");

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

    private void init(View view) {
        rvSaved = view.findViewById(R.id.rv_saved);
        layoutManager = new GridLayoutManager(getContext(), 3);
        rvSaved.setLayoutManager(layoutManager);
        rvSaved.addItemDecoration(new LayoutMarginDecoration(3, 10));
        SavedAdapter savedAdapter = new SavedAdapter(getContext(), getData());
        savedAdapter.notifyDataSetChanged();
        rvSaved.setAdapter(savedAdapter);

    }


}