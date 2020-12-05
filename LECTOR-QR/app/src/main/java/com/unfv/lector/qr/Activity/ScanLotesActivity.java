package com.unfv.lector.qr.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;
import com.unfv.lector.qr.R;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanLotesActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imgBackTool;
    TextView tituloToolBar;
    Button btnLotes;
    private ZXingScannerView mScannerView;
    String datoRecuperado="";
    ArrayList<String> listDatos = new ArrayList();
    LinearLayout linerRespuestaOK, linerIndromacion;
    TextView textViewDataEscaneada;
    Boolean auxContinuar =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_lotes);
        imgBackTool=findViewById(R.id.imgBakTooBar);
        tituloToolBar=findViewById(R.id.tvtituloToolBar);
        btnLotes=findViewById(R.id.btn_scan_lotes);
        linerIndromacion=findViewById(R.id.valor_escaneado_lote);
        linerRespuestaOK=findViewById(R.id.lin_respuesta_escaneo_lote);
        textViewDataEscaneada=findViewById(R.id.tvCodigo_qr_lote);

        tituloToolBar.setText("Escaneo por lotes");
        imgBackTool.setVisibility(View.VISIBLE);
        imgBackTool.setOnClickListener(this);
        btnLotes.setOnClickListener(this);

        ocultarRespuesta();
        checkCameraPermission();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBakTooBar:
                onBackPressed();
                break;
            case R.id.btn_scan_lotes:
                ScanMasivo();
                break;
        }
    }

    //Solicitar los permisos para el uso de la camara
    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }

    //metodo para scannear
    public void ScanMasivo(){
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(new handleResult());
        mScannerView.startCamera();
    }

    public class handleResult implements  ZXingScannerView.ResultHandler {
        @Override
        public void handleResult(Result result) {
            setContentView(R.layout.activity_scan_individual);
            mScannerView.stopCamera();
            datoRecuperado=result.getText();
            if (datoRecuperado != "") {
                auxContinuar=true;
                listDatos.add(datoRecuperado);
                mostrarRespuesta();
                confirmDialog();
            }else{
                ocultarRespuesta();
            }
        };
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Html.fromHtml("<font color='#F07613'> <b>Confirmar</b></font>"));
        builder.setMessage("¿Desea continuar escaneando?\n\nEl valor escaneado: "+datoRecuperado);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ScanMasivo();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), RespuestaScanLotesActivity.class);
                intent.putStringArrayListExtra("miLista", listDatos);
                startActivity(intent);
                finish();
            }
        });
        builder.create().show();
    }

    public void ocultarRespuesta(){
        linerIndromacion.setVisibility(View.GONE);
        linerRespuestaOK.setVisibility(View.GONE);
        textViewDataEscaneada.setText("");
    }

    public void mostrarRespuesta(){
        linerIndromacion.setVisibility(View.VISIBLE);
        linerRespuestaOK.setVisibility(View.VISIBLE);
        textViewDataEscaneada.setText(datoRecuperado);
    }
}
