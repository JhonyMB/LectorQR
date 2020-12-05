package com.unfv.lector.qr.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.unfv.lector.qr.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener  {

    private Toolbar toolbarMenu;
    private CardView cardScannerIndividual, cardScannerLotes, cardListado,cardCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        toolbarMenu = findViewById(R.id.toolbar_menu_principal);
        setSupportActionBar(toolbarMenu);

        cardScannerIndividual= findViewById(R.id.carScannerIndividual);
        cardListado= findViewById(R.id.carListar);
        cardScannerLotes=findViewById(R.id.carScannerLotes);
        cardCerrarSesion=findViewById(R.id.cardCerrarSesion);

        cardScannerLotes.setOnClickListener(this);
        cardListado.setOnClickListener(this);
        cardScannerIndividual.setOnClickListener(this);
        cardCerrarSesion.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.carScannerIndividual:
                startActivity(new Intent(getApplicationContext(),ScanIndividualActivity.class));
                break;
            case R.id.carScannerLotes:
                startActivity(new Intent(getApplicationContext(),ScanLotesActivity.class));
                break;
            case R.id.carListar:
                startActivity(new Intent(getApplicationContext(),ListadoQrActivity.class));
                break;
            case R.id.cardCerrarSesion:
                confirmDialog();
                break;
        }

    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Html.fromHtml("<font color='#F07613'> <b>Confirmar</b></font>"));
        builder.setMessage("¿Desea cerrar sesión?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
