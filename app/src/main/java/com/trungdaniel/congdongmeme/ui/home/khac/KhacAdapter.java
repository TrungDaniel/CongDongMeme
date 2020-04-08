package com.trungdaniel.congdongmeme.ui.home.khac;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trungdaniel.congdongmeme.EditAnhActivity;
import com.trungdaniel.congdongmeme.R;
import com.trungdaniel.congdongmeme.ui.home.Anh;

import java.util.List;

public class KhacAdapter extends RecyclerView.Adapter<KhacAdapter.KhacViewHolder> {
    private Context context;
    private List<Anh> anhList;

    public KhacAdapter(Context context, List<Anh> anhList) {
        this.context = context;
        this.anhList = anhList;
    }

    @NonNull
    @Override
    public KhacViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nguoi_layout, parent, false);
        return new KhacViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhacViewHolder holder, final int position) {
        Glide.with(context).load(anhList.get(position).getUriImage()).placeholder(R.drawable.ic_launcher_background).into(holder.imgKhac);

        holder.imgKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditAnhActivity.class);
                intent.putExtra("urlAnh", anhList.get(position).getUriImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return anhList.size();
    }

    class KhacViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgKhac;

        public KhacViewHolder(@NonNull View itemView) {
            super(itemView);
            imgKhac = itemView.findViewById(R.id.img_item_nguoi);
        }
    }

}
