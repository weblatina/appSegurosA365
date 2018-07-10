package net.weblatina.myapplication;

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
public class MainActivity extends Activity implements OnClickListener{
    Button btn;
    ImageView imagen;
    Intent i;
    final static int cons =0;
    Bitmap bmp;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkCameraPermission();
        init();
    }
    public void init(){
        btn = (Button)findViewById(R.id.btnCaptura);
        btn.setOnClickListener(this);
        imagen = (ImageView)findViewById(R.id.imagen);
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
    public void onClick(View v){
        int id;
        id=v.getId();
        switch (id){
            case R.id.btnCaptura:
                i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,cons);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode==Activity.RESULT_OK){
            Bundle ext = data.getExtras();
            bmp = (Bitmap)ext.get("data");
            imagen.setImageBitmap(bmp);
            imagen.buildDrawingCache();
            Bitmap bitmap = imagen.getDrawingCache();
        }
    }
}
