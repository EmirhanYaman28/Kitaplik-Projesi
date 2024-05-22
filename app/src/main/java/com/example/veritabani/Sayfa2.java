package com.example.veritabani;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Sayfa2 extends AppCompatActivity {

    SQLiteDatabase DBkitap;
    ListView listView;
    ArrayList<String> kitapListesi;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sayfa2);

        listView = findViewById(R.id.listView);
        kitapListesi = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kitapListesi);
        listView.setAdapter(adapter);

        DBkitap = this.openOrCreateDatabase("okul", MODE_PRIVATE, null);

        // Veritabanından verileri al ve ListView'e ekle
        verileriAlVeListeyeEkle();
    }

    private void verileriAlVeListeyeEkle() {
        // Veritabanından tüm verileri sorgula
        Cursor cursor = DBkitap.rawQuery("SELECT * FROM kitap", null);

        // Cursor'da veri varsa
        if (cursor.moveToFirst()) {
            do {
                // Veritabanından gelen verileri al
                String kitapAdi = cursor.getString(cursor.getColumnIndex("ad"));
                int sayfaSayisi = cursor.getInt(cursor.getColumnIndex("sayfa"));
                String yazarAdi = cursor.getString(cursor.getColumnIndex("yazar"));

                // Listeye ekle
                kitapListesi.add("Ad: " + kitapAdi + ", Sayfa: " + sayfaSayisi + ", Yazar: " + yazarAdi);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Veritabanından gelen verileri ListView'e ekledikten sonra, adapter'a değişiklik olduğunu belirt
        adapter.notifyDataSetChanged();
    }
}
