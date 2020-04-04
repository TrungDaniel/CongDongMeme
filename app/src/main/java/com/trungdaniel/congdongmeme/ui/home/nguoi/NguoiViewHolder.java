package com.trungdaniel.congdongmeme.ui.home.nguoi;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trungdaniel.congdongmeme.EditAnhActivity;
import com.trungdaniel.congdongmeme.R;

public class NguoiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView imgItemNguoi;


    public NguoiViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        imgItemNguoi = itemView.findViewById(R.id.img_item_nguoi);

    }

    @Override
    public void onClick(View view) {

    }
}
