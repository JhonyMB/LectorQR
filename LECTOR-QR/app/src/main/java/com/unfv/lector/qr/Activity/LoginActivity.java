package com.unfv.lector.qr.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unfv.lector.qr.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtUser, txtPass;
    String ltxtPass, ltxtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=findViewById(R.id.btn_ingreso_login);
        txtPass=findViewById(R.id.txt_password);
        txtUser=findViewById(R.id.txt_usuario);
        //Bloquear salto de linea
        txtPass.setSingleLine(false);
        txtPass.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);

        txtUser.setSingleLine(false);
        txtUser.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);

        chekPermisoInternet();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ltxtUser=txtUser.getText().toString().trim();
                ltxtPass=txtPass.getText().toString().trim();

                if(ltxtPass.isEmpty() || ltxtUser.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Completar los campos",Toast.LENGTH_SHORT).show();

                }else{
                    // iniciarSesion();
                    txtPass.setText("");
                    txtUser.setText("");
                    startActivity(new Intent(getApplicationContext(),PrincipalActivity.class));
                }
            }
        });
    }

    public void chekPermisoInternet(){

    }
}
