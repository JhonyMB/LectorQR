package com.unfv.lector.qr.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unfv.lector.qr.BD.ConexionSQLiteHelper;
import com.unfv.lector.qr.R;
import com.unfv.lector.qr.Utilidades.Constantes;

import java.util.HashMap;
import java.util.Map;

public class RespuestaScanIndividualActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvCodigoQr;
    Button btnSiguienteScan;
    String datoRecuperado;
    LinearLayout linRpta,linQr;
    int auxScanAut,auxScanMan;
    ImageView imgBackTool;
    //Firebase
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta_scan_individual);
        imgBackTool=findViewById(R.id.imgBakTooBar);

        imgBackTool.setVisibility(View.VISIBLE);
        imgBackTool.setOnClickListener(this);
        linRpta= findViewById(R.id.lin_respuesta_escaneo);
        linQr= findViewById(R.id.lin_qr);
        btnSiguienteScan= findViewById(R.id.btn_scan_siguiente);
        tvCodigoQr= findViewById(R.id.tvCodigo_qr);
        btnSiguienteScan.setOnClickListener(this);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        //RECUPERAMOS QR DE ESCANEAR AUTOMATICO INDIVIDUAL
        datoRecuperado=getIntent().getStringExtra("OtAutomatico");
        auxScanAut=getIntent().getIntExtra("auxiliarScan",0);

        if(auxScanAut==2){
            linRpta.setVisibility(View.VISIBLE);
            linQr.setVisibility(View.VISIBLE);
        }
        tvCodigoQr.setText(""+datoRecuperado);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan_siguiente:
                confirmDialog();
                break;
            case R.id.imgBakTooBar:
                onBackPressed();
                break;
        }
    }

    private void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Html.fromHtml("<font color='#F07613'> <b>Confirmar</b></font>"));
        builder.setMessage("¿Desea registrar la información?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                registrarUsuariosQR();
                saveDataFireBase();
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

    private void registrarUsuariosQR(){
        //bd
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios_qr",null,1);
        SQLiteDatabase db= conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constantes.CAMPO_DATA, datoRecuperado);

        Long idResultante=db.insert(Constantes.TABLA_USARIO_QR,Constantes.CAMPO_ID,values);
        db.close();

        if(idResultante!=0){
            Toast.makeText(getApplicationContext(),"Se registró correctamente. ",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),PrincipalActivity.class));
        }else{
            Toast.makeText(getApplicationContext(),"No se registro corrextamente. ",Toast.LENGTH_SHORT).show();
        }
    }

    public void saveDataFireBase(){
        Map<String,Object> datosUsuario=new HashMap<>();
        datosUsuario.put("valor",datoRecuperado);

        databaseReference.child("UsuarioQR").push().setValue(datosUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Se grabo Corrextamente. ",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"No se grabo Corrextamente. ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
