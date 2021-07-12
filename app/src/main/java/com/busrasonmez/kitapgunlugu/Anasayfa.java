package com.busrasonmez.kitapgunlugu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Trace;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Anasayfa extends AppCompatActivity {

    Toolbar toolbar;
    Button btnkutuphane, btnokudu, btnokuyacak, btnistatistik;
    EditText edt_soz, anlamlisoz;
    TextView sozekletxt;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<String> sozler = new ArrayList<>();
    Veritabani veritabani = new Veritabani(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        NesneDahilEtme();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ANASAYFA");

        registerForContextMenu(edt_soz);

        sozler = (ArrayList<String>)veritabani.KayitliSoz();
        for(int i = 0;i<sozler.size();i++){
            sozekletxt.setText(sozler.get(i));
            edt_soz.setText(sozler.get(i));
        }



    }

    public void NesneDahilEtme(){

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        btnkutuphane = findViewById(R.id.btnkutuphane);
        btnokudu = findViewById(R.id.btnokudu);
        btnokuyacak = findViewById(R.id.btnokuyacak);
        btnistatistik = findViewById(R.id.btnistatistik);
        edt_soz = (EditText)findViewById(R.id.anlamli_soz2);
        sozekletxt = findViewById(R.id.anlamli_soztxt);
        anlamlisoz = findViewById(R.id.anlamli_soz2);

    }

    public void SozEkle(View view){
        sozekletxt.setVisibility(View.INVISIBLE);
        anlamlisoz.setVisibility(View.VISIBLE);
        edt_soz.setVisibility(View.VISIBLE);
    }

    public void SozKaydet(View view){

        String soz1 = edt_soz.getText().toString();
        sozekletxt.setVisibility(View.VISIBLE);
        edt_soz.setVisibility(View.INVISIBLE);
        anlamlisoz.setVisibility(View.INVISIBLE);
        sozekletxt.setText(soz1);

        Veritabani veritabani = new Veritabani(this);
        Kullanici kullanici = new Kullanici(soz1,8);
        veritabani.SozGuncelle(kullanici);

    }

    public void SozIptal(View view){
        sozekletxt.setVisibility(View.VISIBLE);
        edt_soz.setVisibility(View.INVISIBLE);
        anlamlisoz.setVisibility(View.INVISIBLE);
        edt_soz.setText(sozekletxt.getText());
    }

    public void KutuphaneyeGit(View view){
        Intent intent = new Intent(getApplicationContext(),KutuphaneActivity.class);
        startActivity(intent);
    }

    public void OkunanKitaplaraGit(View view){
        Intent intent = new Intent(getApplicationContext(),OkunanKitaplar.class);
        startActivity(intent);
    }

    public void HedefeGit(View view){
        Intent intent = new Intent(getApplicationContext(),Hedef.class);
        startActivity(intent);
    }

    public void OkunacakKitaplaraGit(View view){
        Intent intent = new Intent(getApplicationContext(),OkunacakKitaplar.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.anasayfa_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.profil:
                intent = new Intent(Anasayfa.this,ProfilActivity.class);
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
        menuInflater.inflate(R.menu.liste_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        final  AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.sil2:
                edt_soz.setText("");
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Uygulamadan çıkmak istiyor musunuz?")
                .setCancelable(false)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("UYARI!");
        alert.show();
    }
}