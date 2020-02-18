package com.example.cobarecycleview3;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cobarecycleview3.model.Hotel;

public class InputActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_GET = 1;
    EditText etJudul;
    EditText etDeskripsi;
    EditText etDetail;
    EditText etLokasi;
    ImageView ivFoto;
    Uri uriFoto;
    Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        etJudul = findViewById(R.id.editTextNama);
        etDeskripsi = findViewById(R.id.editTextDeskripsi);
        etDetail = findViewById(R.id.editTextDetail);
        etLokasi = findViewById(R.id.editTextLokasi);
        ivFoto = findViewById(R.id.imageViewFoto);

        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
            }
        });

        findViewById(R.id.buttonSimpan).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doSave();
                    }
                }
        );
    }
}
