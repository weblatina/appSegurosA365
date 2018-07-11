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
import android.widget.Toast;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
            try {
                Bundle ext = data.getExtras();
                bmp = (Bitmap)ext.get("data");
                imagen.setImageBitmap(bmp);
                imagen.buildDrawingCache();
                Bitmap bitmap = imagen.getDrawingCache();

                Vision.Builder visionBuilder = new Vision.Builder(
                        new NetHttpTransport(),
                        new AndroidJsonFactory(),
                        null);
                visionBuilder.setVisionRequestInitializer(
                        new VisionRequestInitializer("AIzaSyA5y6vCsWvlrJ45kpF8NC1npaJ0EhCqlTs"));
                Vision vision = visionBuilder.build();

                Image base64EncodedImage = getBase64EncodedJpeg(bitmap);

                Feature labelDetection = new Feature();
                labelDetection.setType("LABEL_DETECTION");
                labelDetection.setMaxResults(1);

                AnnotateImageRequest request = new AnnotateImageRequest();
                request.setImage(base64EncodedImage);
                request.setFeatures(Arrays.asList(labelDetection));

                BatchAnnotateImagesRequest batchRequest =
                        new BatchAnnotateImagesRequest();

                batchRequest.setRequests(Arrays.asList(request));


                BatchAnnotateImagesResponse response =
                        vision.images().annotate(batchRequest).execute();


                List<EntityAnnotation> texts = response.getResponses().get(0)
                        .getTextAnnotations();

                //aquí se muestra la imagen
                if (texts.get(0).getDescription().contains("car")){
                    Toast.makeText(this, "En la foto aparece un vehículo", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "En la foto NO aparece un vehículo", Toast.LENGTH_LONG).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Image getBase64EncodedJpeg(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new Image().encodeContent(byteArray);
    }
}
