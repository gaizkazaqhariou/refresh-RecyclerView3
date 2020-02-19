package com.example.cobarecycleview3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cobarecycleview3.model.Hotel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

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
        hotel = (Hotel) getIntent().getSerializableExtra(MainActivity.HOTEL);
        if (hotel != null) {
            setTitle("Edit " + hotel.judul);
            fillData();
        } else {
            setTitle("New Hotel");
        }
    }

    private void fillData() {
        etJudul.setText(hotel.judul);
        etDeskripsi.setText(hotel.deskripsi);
        etDetail.setText(hotel.detail);
        etLokasi.setText(hotel.lokasi);
        uriFoto = Uri.parse(hotel.foto);
        ivFoto.setImageURI(uriFoto);
    }

    private void doSave() {
        String judul = etJudul.getText().toString();
        String deskripsi = etDeskripsi.getText().toString();
        String detail = etDetail.getText().toString();
        String lokasi = etLokasi.getText().toString();

        if (isValid(judul, deskripsi, detail, lokasi, uriFoto)) {
            hotel = new Hotel(judul, deskripsi, detail, lokasi, uriFoto.toString());

            Intent intent = new Intent();
            intent.putExtra(MainActivity.HOTEL, hotel);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean isValid(String judul, String deskripsi, String detail, String lokasi, Uri uriFoto) {
        boolean valid = true;
        if (judul.isEmpty()) {
            setErrorEmpty(etJudul);
            valid = false;
        }
        if (deskripsi.isEmpty()) {
            setErrorEmpty(etDeskripsi);
            valid = false;
        }
        if (detail.isEmpty()) {
            setErrorEmpty(etDetail);
            valid = false;
        }
        if (lokasi.isEmpty()) {
            setErrorEmpty(etLokasi);
            valid = false;
        }
        if (uriFoto == null) {
            Snackbar.make(ivFoto, "Foto Belum Ada", Snackbar.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    private void setErrorEmpty(EditText editText) {
        editText.setError(((TextInputLayout) editText.getParent().getParent()).getHint() + "Belum Diisi");
    }

    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            uriFoto = data.getData();
            ivFoto.setImageURI(uriFoto);
        }
    }
}
