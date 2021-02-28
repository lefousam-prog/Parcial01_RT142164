package sv.edu.udb.parcial01_rt142164;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class segundaActivity extends AppCompatActivity {

    ImageView ivpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        /*Creamos el variable del elemento donde figuraremos la foto*/
        ivpic = findViewById(R.id.ivPicture);

        /*A traves de un Bundle capturamos la foto enviada por el intent a
         * este activity*/
        Bundle bundle = getIntent().getExtras();


        /*Capturamos la variable que contenia la foto en el intent
         * y la casteamos a Bitman para luego dibujarla en la ImageView*/
        Bitmap bitmap = (Bitmap) bundle.get("picture");
        ivpic.setImageBitmap(bitmap);
    }

    /*Por ultimo al darle click al boton este finalizara esta actividad, retornando a la
     * segundaActividad que estaba pausada*/
    public void Finalizar(View view) {
        finish();
    }
}