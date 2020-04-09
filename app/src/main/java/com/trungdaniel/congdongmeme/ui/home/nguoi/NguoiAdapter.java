package com.trungdaniel.congdongmeme.ui.home.nguoi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.trungdaniel.congdongmeme.EditAnhActivity;
import com.trungdaniel.congdongmeme.R;
import com.trungdaniel.congdongmeme.ui.home.Anh;

import java.util.List;

public class NguoiAdapter extends RecyclerView.Adapter<NguoiViewHolder> {
    private Context context;
    private List<Anh> anhList;
    private NavController navController;

    public NguoiAdapter(Context context, List<Anh> anhList) {
        this.context = context;
        this.anhList = anhList;
    }

    @NonNull
    @Override
    public NguoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nguoi_layout, parent, false);
        NguoiViewHolder nguoiViewHolder = new NguoiViewHolder(view);
        navController = Navigation.findNavController(parent);
        return nguoiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiViewHolder holder, final int position) {
        Glide.with(context).load(anhList.get(position).getUriImage()).placeholder(R.drawable.img_loading_image).transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgItemNguoi);

        holder.imgItemNguoi.setOnClickListener(new View.OnClickListener() {
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
}
