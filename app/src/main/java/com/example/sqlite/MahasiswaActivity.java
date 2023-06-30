package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MahasiswaActivity extends AppCompatActivity {
    EditText nim, nama, jeniskelamin, alamat, email;
    Button simpan, tampil, hapus, edit;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        nim = findViewById(R.id.edtnim);
        nama = findViewById(R.id.edtnama);
        jeniskelamin = findViewById(R.id.edtjk);
        alamat = findViewById(R.id.edtalamat);
        email = findViewById(R.id.edtemail);
        simpan = findViewById(R.id.btnsimpan);
        tampil = findViewById(R.id.btntampil);
        hapus = findViewById(R.id.btnhapus);
        edit = findViewById(R.id.btnedit);
        db = new DBHelper(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtnim = nim.getText().toString();
                String txtnama = nama.getText().toString();
                String txtjk = jeniskelamin.getText().toString();
                String txtalamat = alamat.getText().toString();
                String txtemail = email.getText().toString();

                if (TextUtils.isEmpty(txtnim) || TextUtils.isEmpty(txtnama) || TextUtils.isEmpty(txtjk)
                        || TextUtils.isEmpty(txtalamat) || TextUtils.isEmpty(txtemail)) {
                    Toast.makeText(MahasiswaActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    boolean checkkode = db.checknim(txtnim);
                    if (!checkkode) {
                        boolean insert = db.insertData(txtnim, txtnama, txtjk, txtalamat, txtemail);
                        if (insert) {
                            Toast.makeText(MahasiswaActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MahasiswaActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MahasiswaActivity.this, "Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampildata();
                if(res.getCount() == 0){
                    Toast.makeText(getApplicationContext(), "Tidak ada data.", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("NIM Mahasiswa: " + res.getString(0) + "\n");
                    buffer.append("Nama Mahasiswa: " + res.getString(1) + "\n");
                    buffer.append("Jenis Kelamin Mahasiswa: " + res.getString(2) + "\n");
                    buffer.append("Alamat Mahasiswa: " + res.getString(3) + "\n");
                    buffer.append("Email Mahasiswa: " + res.getString(4) + "\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MahasiswaActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Biodata Mahasiswa");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nimToDelete = nim.getText().toString();

                boolean isDeleted = db.deleteData(nimToDelete);

                if (isDeleted) {
                    Toast.makeText(MahasiswaActivity.this, "Record deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MahasiswaActivity.this, "Failed to delete record", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nimToUpdate = nim.getText().toString();
                String newNama = nama.getText().toString();
                String newJenisKelamin = jeniskelamin.getText().toString();
                String newAlamat = alamat.getText().toString();
                String newEmail = email.getText().toString();

                if (TextUtils.isEmpty(nimToUpdate) || TextUtils.isEmpty(newNama) || TextUtils.isEmpty(newJenisKelamin)
                        || TextUtils.isEmpty(newAlamat) || TextUtils.isEmpty(newEmail)) {
                    Toast.makeText(MahasiswaActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                } else {
                    boolean isUpdated = db.updateData(nimToUpdate, newNama, newJenisKelamin, newAlamat, newEmail);

                    if (isUpdated) {
                        Toast.makeText(MahasiswaActivity.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MahasiswaActivity.this, "Data gagal diupdate", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}