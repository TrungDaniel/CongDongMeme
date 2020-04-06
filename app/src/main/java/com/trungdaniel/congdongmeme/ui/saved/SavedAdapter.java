package com.trungdaniel.congdongmeme.ui.saved;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trungdaniel.congdongmeme.R;

import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SavedViewHoder> {
    private Context context;
    List<Saved> savedList;

    public SavedAdapter(Context context, List<Saved> savedList) {
        this.context = context;
        this.savedList = savedList;
    }

    @NonNull
    @Override
    public SavedViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nguoi_layout, parent, false);
        return new SavedViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHoder holder, final int position) {
        Glide.with(context).load(savedList.get(position).getUri().toString()).placeholder(R.drawable.ic_launcher_background).into(holder.imgSavedAnh);
        holder.imgSavedAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(savedList.get(position).getUri(), "image/*");


                /*try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "chức năng bị lỗi, xin vui lòng báo cáo đến mình ", Toast.LENGTH_SHORT).show();

                }*/
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return savedList.size();
    }

    class SavedViewHoder extends RecyclerView.ViewHolder {
        private ImageView imgSavedAnh;

        public SavedViewHoder(@NonNull View itemView) {
            super(itemView);
            imgSavedAnh = itemView.findViewById(R.id.img_item_nguoi);
        }
    }
}
