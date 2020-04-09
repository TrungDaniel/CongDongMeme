package com.trungdaniel.congdongmeme.ui.home.convat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.trungdaniel.congdongmeme.EditAnhActivity;
import com.trungdaniel.congdongmeme.R;
import com.trungdaniel.congdongmeme.ui.home.Anh;
import com.trungdaniel.congdongmeme.ui.saved.Saved;

import java.util.List;

public class ConVatAdapter extends RecyclerView.Adapter<ConVatAdapter.ConVatViewHolder> {
    private Context context;
    private List<Anh> anhList;

    public ConVatAdapter(Context context, List<Anh> anhList) {
        this.context = context;
        this.anhList = anhList;
    }

    @NonNull
    @Override
    public ConVatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nguoi_layout, parent, false);
        return new ConVatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConVatViewHolder holder, final int position) {
        Glide.with(context).load(anhList.get(position).getUriImage()).placeholder(R.drawable.img_loading_image).transition(DrawableTransitionOptions.withCrossFade()).into(holder.imgConVat);

        holder.imgConVat.setOnClickListener(new View.OnClickListener() {
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


    public class ConVatViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgConVat;

        public ConVatViewHolder(@NonNull View itemView) {
            super(itemView);
            imgConVat = itemView.findViewById(R.id.img_item_nguoi);
        }
    }

}
