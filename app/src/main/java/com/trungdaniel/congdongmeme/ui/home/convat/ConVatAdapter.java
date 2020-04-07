package com.trungdaniel.congdongmeme.ui.home.convat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trungdaniel.congdongmeme.R;
import com.trungdaniel.congdongmeme.ui.home.Anh;
import com.trungdaniel.congdongmeme.ui.saved.Saved;

import java.util.List;

public class ConVatAdapter extends RecyclerView.Adapter<ConVatAdapter.ConVatViewHolder> {
    private Context context;
    List<Saved> savedList;

    public ConVatAdapter() {
    }

    public ConVatAdapter(Context context, List<Saved> savedList) {
        this.context = context;
        this.savedList = savedList;
    }

    @NonNull
    @Override
    public ConVatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nguoi_layout, parent, false);
        return new ConVatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConVatViewHolder holder, int position) {
        Glide.with(context).load(savedList.get(position).getUri().toString()).placeholder(R.drawable.ic_launcher_background).into(holder.imgConVat);
    }

    @Override
    public int getItemCount() {
        return savedList.size();
    }


    public class ConVatViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgConVat;

        public ConVatViewHolder(@NonNull View itemView) {
            super(itemView);
            imgConVat = itemView.findViewById(R.id.img_item_nguoi);
        }
    }

}
