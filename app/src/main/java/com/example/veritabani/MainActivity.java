package com.example.veritabani;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase DBkitap;
    Button ekleme, silme, guncelle, listele;
    EditText ad, sayfa, yazar, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ekleme = findViewById(R.id.button);
        silme = findViewById(R.id.btnSil);
        guncelle = findViewById(R.id.btnGuncelle);
        listele = findViewById(R.id.btnListele);
        ad = findViewById(R.id.adPT);
        sayfa = findViewById(R.id.sayfaPT);
        yazar = findViewById(R.id.yazarPT);
        no = findViewById(R.id.noPt);

        DBkitap = this.openOrCreateDatabase("okul", MODE_PRIVATE, null);
        String kitapList = "CREATE TABLE IF NOT EXISTS kitap(id INTEGER PRIMARY KEY,ad VARCHAR, sayfa INTEGER ,yazar VARCHAR)";
        DBkitap.execSQL(kitapList);

        ekleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kitapEkle = "INSERT INTO kitap(ad,sayfa,yazar) VALUES(?,?,?)";
                SQLiteStatement eklemeSorgusu = DBkitap.compileStatement(kitapEkle);
                eklemeSorgusu.bindString(1, ad.getText().toString());
                eklemeSorgusu.bindLong(2, Long.parseLong(sayfa.getText().toString()));
                eklemeSorgusu.bindString(3, yazar.getText().toString());

                eklemeSorgusu.execute();

                ad.setText("");
                sayfa.setText("");
                yazar.setText("");
                Toast.makeText(MainActivity.this, "Eklendi", Toast.LENGTH_SHORT).show();
            }
        });

        silme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kitapSil = "DELETE FROM kitap WHERE id=?";
                SQLiteStatement silSorgu = DBkitap.compileStatement(kitapSil);
                silSorgu.bindLong(1, Integer.parseInt(no.getText().toString()));
                silSorgu.execute();
                no.setText("");
                Toast.makeText(MainActivity.this, "Silindi", Toast.LENGTH_SHORT).show();
            }
        });

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kitapGuncelle = "UPDATE kitap SET ad=?,sayfa=?,yazar=? WHERE id=?";
                SQLiteStatement guncSorgu = DBkitap.compileStatement(kitapGuncelle);
                guncSorgu.bindString(1, ad.getText().toString());
                guncSorgu.bindString(2, sayfa.getText().toString());
                guncSorgu.bindString(3, yazar.getText().toString());
                guncSorgu.bindLong(4, Integer.parseInt(no.getText().toString()));
                guncSorgu.execute();

                ad.setText("");
                sayfa.setText("");
                yazar.setText("");
                no.setText("");

                Toast.makeText(MainActivity.this, "Güncellendi", Toast.LENGTH_SHORT).show();
            }
        });

        listele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Sayfa2.class);
                startActivity(intent);
            }
        });
    }
}
