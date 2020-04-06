package com.trungdaniel.congdongmeme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditAnhActivity extends AppCompatActivity {
    private ImageView imgEditAnh;
    private Button btnLuuAnh;
    private Bitmap bitmap;
    private TextView tvText1, tvText2;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_anh);
        init();
        setAnh();
        luuAnh();
        MobileAds.initialize(this, "ca-app-pub-6851769271647416~9601900263");
        adView = findViewById(R.id.adviewbanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
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

                    fileOutputStream = new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(outFile));
                    sendBroadcast(intent);
                    new AlertDialog.Builder(EditAnhActivity.this)
                            .setTitle("Lưu ảnh thành công")
                            .setMessage("Ảnh đã được lưu vào thư mục Meme")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

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
        tvText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(EditAnhActivity.this);
                final EditText edittext = new EditText(EditAnhActivity.this);
                alert.setMessage("viết gì đó thú vị nhé");
                alert.setTitle("Dòng 1");
                alert.setView(edittext);
                alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        Editable YouEditTextValue = edittext.getText();
                        //OR
                        String text = edittext.getText().toString();
                        tvText1.setText(text);
                    }
                });

                alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //
                    }
                });

                alert.show();
            }
        });
        tvText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(EditAnhActivity.this);
                final EditText edittext = new EditText(EditAnhActivity.this);
                alert.setMessage("viết gì đó thú vị nhé");
                alert.setTitle("Dòng 2");
                alert.setView(edittext);
                alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        Editable YouEditTextValue = edittext.getText();
                        //OR
                        String text = edittext.getText().toString();
                        tvText2.setText(text);

                    }
                });

                alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //
                    }
                });

                alert.show();
            }
        });
    }

    private void init() {
        imgEditAnh = findViewById(R.id.img_edit_anh);
        btnLuuAnh = findViewById(R.id.btn_luu_anh);
        tvText1 = findViewById(R.id.tv_anh_1);
        tvText2 = findViewById(R.id.tv_anh_2);

    }
}
