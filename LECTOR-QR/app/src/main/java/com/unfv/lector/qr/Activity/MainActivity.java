package com.unfv.lector.qr.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.unfv.lector.qr.R;

public class MainActivity extends AppCompatActivity {

    ImageView imgSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgSplash= findViewById(R.id.img_splas);

        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //DECLARAMOS LA ANIMACION Y RECUPERAMOS LA ANIMACION
        Animation animacion= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_back_in);
        imgSplash.startAnimation(animacion);
        final Intent inten=new Intent(this,LoginActivity.class);

        Thread timer=new Thread(){

            @Override
            public void run() {
                try {
                    sleep(3000);
                }catch (Exception e){

                }finally {
                    startActivity(inten);
                    finish();
                }
            }
        };

        timer.start();
    }
}
