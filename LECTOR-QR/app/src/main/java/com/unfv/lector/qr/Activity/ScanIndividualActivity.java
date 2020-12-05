package com.unfv.lector.qr.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.Result;
import com.unfv.lector.qr.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanIndividualActivity extends AppCompatActivity implements View.OnClickListener{

    private ZXingScannerView mScannerView;
    String datoRecuperado="";
    ImageView imgBackTool;
    TextView tituloToolBar;
    Button btnEscannerInvidual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_individual);
        imgBackTool=findViewById(R.id.imgBakTooBar);
        tituloToolBar=findViewById(R.id.tvtituloToolBar);
        btnEscannerInvidual=findViewById(R.id.btn_scanIndividual);
        tituloToolBar.setText("Escaneo individual");

        imgBackTool.setVisibility(View.VISIBLE);
        imgBackTool.setOnClickListener(this);
        btnEscannerInvidual.setOnClickListener(this);

        checkCameraPermission();
    }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBakTooBar:
                onBackPressed();
                break;
            case R.id.btn_scanIndividual:
                scannearIndividual();
                break;
        }
    }

    public void scannearIndividual() {
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
                Intent in= new Intent(getApplicationContext(),RespuestaScanIndividualActivity.class);
                in.putExtra("auxiliarScan",2);
                in.putExtra("OtAutomatico",datoRecuperado);
                startActivity(in);
                finish();
            }else{
                confirmDialog();
            }
        };
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
