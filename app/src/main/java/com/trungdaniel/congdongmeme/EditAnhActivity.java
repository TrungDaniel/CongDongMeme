package com.trungdaniel.congdongmeme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditAnhActivity extends AppCompatActivity {
    private ImageView imgEditAnh;
    private Button btnLuuAnh;
    private BitmapDrawable bitmapDrawable;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_anh);
        init();
        setAnh();
        luuAnh();
    }

    private void luuAnh() {
        btnLuuAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.ll_edit_anh);
                layout.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(layout.getDrawingCache());
                layout.setDrawingCacheEnabled(false);

               /* bitmapDrawable = (BitmapDrawable) imgEditAnh.getDrawable();
                bitmap = bitmapDrawable.getBitmap();*/

                FileOutputStream fileOutputStream = null;

                File fileSave = Environment.getExternalStorageDirectory();
                File directory = new File(fileSave.getAbsolutePath() + "/Meme");
                directory.mkdir();
                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(directory, fileName);

                try {
                    Toast.makeText(EditAnhActivity.this, "bạn đã lưu ảnh thành công", Toast.LENGTH_SHORT).show();
                    fileOutputStream = new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(outFile));
                    sendBroadcast(intent);
                    finish();
                    Intent intentGoto = new Intent();
                    intentGoto.setType("image/*");
                    startActivity(intentGoto);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setAnh() {
        Intent intent = getIntent();
        String urlImage = intent.getStringExtra("urlAnh");
        Glide.with(getApplicationContext()).load(urlImage).into(imgEditAnh);
    }

    private void init() {
        imgEditAnh = findViewById(R.id.img_edit_anh);
        btnLuuAnh = findViewById(R.id.btn_luu_anh);

    }
}
