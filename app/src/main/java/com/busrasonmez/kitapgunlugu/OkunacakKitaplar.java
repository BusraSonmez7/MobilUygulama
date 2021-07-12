package com.busrasonmez.kitapgunlugu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class OkunacakKitaplar extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<KutuphaneListview> kitaplararray =  new ArrayList<KutuphaneListview>();
    Veritabani veritabani;
    ListviewAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okunacak_kitaplar);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        listView = findViewById(R.id.kitaplar2);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("OKUNACAK KİTAPLAR");

        veritabani = new Veritabani(this);
        this.kitaplararray = veritabani.OkunanOkunacakKitaplariListele(2);
        adapter = new ListviewAdaptor(this,kitaplararray);

        if(listView!=null){
            listView.setAdapter(adapter);
            registerForContextMenu(listView);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(OkunacakKitaplar.this,KitapOzellikleriActivity.class);
                intent2.putExtra("kitapId",kitaplararray.get(position).getKitapId());
                startActivity(intent2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.kutuphane_toolbar,menu);

        MenuItem item = menu.findItem(R.id.arama);

        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.profil:
                intent = new Intent(OkunacakKitaplar.this,ProfilActivity.class);
                startActivity(intent);
                return true;
            case R.id.sil:

                AlertDialog.Builder alert;
                alert= new AlertDialog.Builder(OkunacakKitaplar.this);
                alert.setTitle("Silme işlemi");
                alert.setMessage("Eklemiş olduğunuz tüm kitapları silmek istediğinize emin misiniz?");
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        veritabani.OkunanOkunacakKitaplariSil(2);
                        Intent intent = new Intent(OkunacakKitaplar.this,OkunacakKitaplar.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();

                return true;
            case R.id.anasayfa:
                intent = new Intent(OkunacakKitaplar.this,Anasayfa.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.cikis:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Uygulamadan çıkmak istiyor musunuz?")
                        .setCancelable(false)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert4 = builder.create();
                alert4.setTitle("UYARI!");
                alert4.show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}