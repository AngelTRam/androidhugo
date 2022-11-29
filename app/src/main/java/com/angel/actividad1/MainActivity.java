package com.angel.actividad1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.angel.actividad1.R;


public class MainActivity extends AppCompatActivity {
    Button explicito;
    EditText lugar;
    Button implicito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("ciclo", "onCreate()");

        explicito = findViewById(R.id.btn_explicito);
        implicito = findViewById(R.id.btn_implicito);
        lugar = findViewById(R.id.txtLugar);

        /*explicito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               methodExplicito();
            }
        });*/
        implicito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscardorImplicito();
            }
        });


    }

    private void metodoExplicito() {
        //Intent intent = new Intent(MainActivity.this, Actividad2.class);

    }

    private void BuscardorImplicito() {
        /*Uri uri = Uri.parse("geo:0,0?q=" + lugar.getText().toString());
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.putExtra(Intent.EXTRA_TEXT, lugar.getText().toString());
        intent2.setType("text/plain");
        startActivity(intent2);*/

        //ACTION_DIAL NOS SIRVE PARA ABRIR EL TECLADO NUMERICO, SI UTILIZAMMOS ACTION_CALL REALIZA UNA LLAMADA EN AUTOMATICO
        Intent intent3 = new Intent();
        intent3.setAction(Intent.ACTION_DIAL);
        intent3.setData(Uri.parse("tel:" + lugar.getText().toString()));
        startActivity(intent3);

    }


    /*private void methodExplicito() {
        Intent intent = new Intent(MainActivity.this, IntentActivity.class);
        intent.putExtra("nombre", "Angel Tapia");
        intent.putExtra("edad", 22);
        startActivity(intent);
        finish();
    }*/
}
