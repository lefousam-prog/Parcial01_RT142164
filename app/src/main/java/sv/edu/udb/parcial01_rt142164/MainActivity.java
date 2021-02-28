package sv.edu.udb.parcial01_rt142164;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnfoto;


    /*Permisos para la camara y para tomar foto*/
    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnfoto = findViewById(R.id.btnFoto);

        /*Evento click al presionar el boton de tomar foto*/
        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Si la api que estamos ocupando es mayor que la version 23 de marshmallow entonces
                 * no abrira la camara automaticamente sin permisos*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    /*Verificamos si estan habilitados los permisos para la camara*/
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){

                        abrirCamara();
                    }
                    /*Habilitamos los permisos pidiendole al usuario que brinde el permiso*/
                    else{
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
                    }
                }
                else{
                    abrirCamara();
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        /*Evaluamos cuando sucede el cuadro de dialogo donde se le pide al usuario dar o denegar el permiso
         * a la camara*/
        if (requestCode == REQUEST_PERMISSION_CAMERA){

            /*Si el usuario ha dado el permiso para utilizar la camara*/
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                abrirCamara();
            }
            /*Si no ha dado el permiso para utilizar la camara*/
            else{
                Toast.makeText(this, "Necesitas habilidar permisos", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /*Este metodo es el final en este se efectua el que hacer luego de tomar la foto, recibe un
     * requesCode que vendria siendo el REQUEST_IMAGE_CAMERA de la foto tomada y un resultCode
     * que seria el RESULT_OK si ha dado en el check luego de tomar la foto sino daria un RESULT_CANCEL
     * pero no coloque el else para ese caso en que no tome la foto*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAMERA){

            /*Si ha tomado la foto*/
            if (resultCode == Activity.RESULT_OK){

                /*obtenemos la imagen y la casteamos al tipo Bitman para guardar
                 * la imagen en esta variable bitman*/
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                // ivfoto.setImageBitmap(bitmap);

                /*A traves de un Intent llamamos la tercerActividad y le pasamos la foto
                 * tomada con tipo Bitman */
                Intent i=new Intent(this, segundaActivity.class);
                i.putExtra("picture", bitmap);
                startActivity(i);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /*Metodo para abrir la camara*/
    private void abrirCamara( ){

        /*Con este intent ocuparemos el MediaStore como una api que nos permitira dibujar la interfaz de la camara
         * para tomar la foto*/
        Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        /*Con el if verificamos si no una aplicacion de camara para que no truebe este activity*/
        // if (camara.resolveActivity(getPackageManager())==null){
        startActivityForResult(camara,REQUEST_IMAGE_CAMERA);
        //}
    }

}