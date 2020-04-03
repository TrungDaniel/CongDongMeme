package com.trungdaniel.congdongmeme.ui.home.nguoi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trungdaniel.congdongmeme.R;
import com.trungdaniel.congdongmeme.ui.home.Anh;

import java.util.List;

public class NguoiAdapter extends RecyclerView.Adapter<NguoiViewHolder> {
    private Context context;
    private List<Anh> anhList;

    public NguoiAdapter(Context context, List<Anh> anhList) {
        this.context = context;
        this.anhList = anhList;
    }

    @NonNull
    @Override
    public NguoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nguoi_layout, parent, false);
        NguoiViewHolder nguoiViewHolder = new NguoiViewHolder(view);
        return nguoiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiViewHolder holder, int position) {
        Glide.with(context).load(anhList.get(position).getUriImage()).placeholder(R.drawable.ic_launcher_background).into(holder.imgItemNguoi);
    }

    @Override
    public int getItemCount() {
        return anhList.size();
    }
}
