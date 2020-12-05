package com.unfv.lector.qr.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unfv.lector.qr.Adapter.CustomAdapter;
import com.unfv.lector.qr.BD.ConexionSQLiteHelper;
import com.unfv.lector.qr.Entidad.UsuarioQR;
import com.unfv.lector.qr.R;
import com.unfv.lector.qr.Utilidades.Constantes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListadoQrActivity extends AppCompatActivity implements View.OnClickListener{

    ConexionSQLiteHelper conn;
    ArrayList<UsuarioQR> listaUsuarioQr;
    RecyclerView recyclerView=null;
    CustomAdapter customAdapter;
    ImageView emptyImageview;
    TextView noData;
    ImageView imgBackTool;
    TextView tituloToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_qr);
        recyclerView= findViewById(R.id.recyclerView);
        emptyImageview=findViewById(R.id.empty_imageview);
        imgBackTool=findViewById(R.id.imgBakTooBar);
        tituloToolBar=findViewById(R.id.tvtituloToolBar);
        noData=findViewById(R.id.no_data);

        tituloToolBar.setText("Listado de registros");
        imgBackTool.setVisibility(View.VISIBLE);
        imgBackTool.setOnClickListener(this);

        conn=new ConexionSQLiteHelper(this,"bd_usuarios_qr",null,1);
        consultarListaQR();

        customAdapter=new CustomAdapter(this,listaUsuarioQr);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListadoQrActivity.this));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBakTooBar:
                onBackPressed();
                break;
        }
    }

    private void consultarListaQR() {
        SQLiteDatabase db= conn.getReadableDatabase();
        UsuarioQR usuarioQR=null;

        listaUsuarioQr=new ArrayList<UsuarioQR>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Constantes.TABLA_USARIO_QR,null);

        if(cursor.getCount()==0){
            emptyImageview.setVisibility(View.VISIBLE);
            noData.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                usuarioQR=new UsuarioQR();
                usuarioQR.setId(cursor.getInt(0));
                usuarioQR.setDato(cursor.getString(1));
                listaUsuarioQr.add(usuarioQR);
            }
            emptyImageview.setVisibility(View.GONE);
            noData.setVisibility(View.GONE);
        }

    }
}
