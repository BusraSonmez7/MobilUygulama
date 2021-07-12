package com.busrasonmez.kitapgunlugu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class KutuphaneActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    Button btnkitap_ekle;

    ArrayList<KutuphaneListview> kitaplararray =  new ArrayList<KutuphaneListview>();
    Veritabani veritabani;
    ListviewAdaptor adapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kutuphane);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        btnkitap_ekle = findViewById(R.id.btnekle);
        listView = findViewById(R.id.kitaplar2);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KÜTÜPHANE");

        veritabani = new Veritabani(this);
        this.kitaplararray = veritabani.KutuphaneListele();
        adapter = new ListviewAdaptor(this,kitaplararray);

        if(listView!=null){
            listView.setAdapter(adapter);
            registerForContextMenu(listView);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(KutuphaneActivity.this,KitapOzellikleriActivity.class);
                intent2.putExtra("kitapId",kitaplararray.get(position).getKitapId());
                startActivity(intent2);
            }
        });
    }

    public void KitapEkle(View view){
        Intent intent = new Intent(this, KitapEkleActivity.class);
        startActivity(intent);
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
                intent = new Intent(KutuphaneActivity.this,ProfilActivity.class);
                startActivity(intent);
                return true;
            case R.id.sil:

                AlertDialog.Builder alert;
                alert= new AlertDialog.Builder(KutuphaneActivity.this);
                alert.setTitle("Silme işlemi");
                alert.setMessage("Eklemiş olduğunuz tüm kitapları silmek istediğinize emin misiniz?");
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        veritabani.KitaplariSil();
                        Intent intent = new Intent(KutuphaneActivity.this,KutuphaneActivity.class);
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
                intent = new Intent(KutuphaneActivity.this,Anasayfa.class);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.listview_context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.sil:
                AlertDialog.Builder alert;
                alert= new AlertDialog.Builder(KutuphaneActivity.this);
                alert.setTitle("Sil");
                alert.setMessage("Kitabı kalıcı olarak silmek istediğinize emin misiniz?");
                alert.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position;
                        position = (int)menuInfo.id;
                        veritabani.KitapSil(kitaplararray.get(position).getKitapId());
                        kitaplararray.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });

                alert.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

}