package com.unfv.lector.qr.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unfv.lector.qr.BD.ConexionSQLiteHelper;
import com.unfv.lector.qr.R;
import com.unfv.lector.qr.Utilidades.Constantes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RespuestaScanLotesActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listViewLotes;
    Button btnGuardarLote;
    ArrayList<String> listDatos = new ArrayList();
    ImageView imgBackTool;
    TextView tituloToolBar;
    //Firebase
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta_scan_lotes);
        listViewLotes=findViewById(R.id.lvLotes);
        btnGuardarLote=findViewById(R.id.btn_guardar_lotes);
        imgBackTool=findViewById(R.id.imgBakTooBar);
        tituloToolBar=findViewById(R.id.tvtituloToolBar);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        tituloToolBar.setText("Listado de escaneos");
        imgBackTool.setVisibility(View.VISIBLE);
        imgBackTool.setOnClickListener(this);
        btnGuardarLote.setOnClickListener(this);

        listDatos = (ArrayList<String>) getIntent().getSerializableExtra("miLista");
        ArrayAdapter adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,listDatos);
        listViewLotes.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBakTooBar:
                onBackPressed();
                break;
            case R.id.btn_guardar_lotes:
                registrarUsuariosQR();
                saveDataFireBase();
                finish();
                break;
        }
    }

    private void registrarUsuariosQR(){
        Long idResultante=null;
        //bd
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios_qr",null,1);
        SQLiteDatabase db= conn.getWritableDatabase();
        ContentValues values=new ContentValues();

        //Recorrer la lista para guardar en la BD local
        for(int i=0; i<listDatos.size(); i++){
            values.put(Constantes.CAMPO_DATA, listDatos.get(i));
            idResultante=db.insert(Constantes.TABLA_USARIO_QR,Constantes.CAMPO_ID,values);
        }
        db.close();

        if(idResultante!=0 && idResultante!=null){
            Toast.makeText(getApplicationContext(),"Se registró correctamente. ",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),PrincipalActivity.class));
        }else{
            Toast.makeText(getApplicationContext(),"No se registro corrextamente. ",Toast.LENGTH_SHORT).show();
        }
    }

    public void saveDataFireBase(){
        Map<String,Object> datosUsuario=new HashMap<>();

        for(int i=0; i<listDatos.size(); i++){
            datosUsuario.put("valor",listDatos.get(i));
            databaseReference.child("UsuarioQR").push().setValue(datosUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Se grabo Correctamente. ",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"No se grabo Correctamente. ",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
