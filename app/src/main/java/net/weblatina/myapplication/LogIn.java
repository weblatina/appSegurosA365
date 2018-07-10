package net.weblatina.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        login();
    }

    public void login() {

        Button Login = (Button) findViewById(R.id.botonEntrar);
        Login.setOnClickListener (new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText user=(EditText) findViewById(R.id.usuario);
                String usuario = user.getText().toString();
                EditText pass=(EditText) findViewById(R.id.clave);
                String password = pass.getText().toString();
                String user_stored="usuario";
                String pass_stored="123";


                if (usuario.equals(user_stored) & password.equals(pass_stored)){

                    Toast.makeText(LogIn.this,"Bienvenido.", Toast.LENGTH_SHORT).show();
                    change_activity();

                }

                else {


                    Toast.makeText(LogIn.this,"Usuario o clave incorrecto.", Toast.LENGTH_SHORT).show();

                }
            }

        });
    }

    public void change_activity () {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent); // Cambio de aplicación
    }

}
