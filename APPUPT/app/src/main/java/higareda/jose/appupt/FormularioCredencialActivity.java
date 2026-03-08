package higareda.jose.appupt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FormularioCredencialActivity extends AppCompatActivity {

    EditText etCurp, etNss, etCarrera, etGrupo;
    Button btnSeleccionarFoto, btnEnviar, btnConsultarEstado;
    ImageView imgFoto;

    Uri imageUri;

    String URL = "http://192.168.0.108/credenciales/guardar_datos_estudiante.php";

    int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_credencial);

        etCurp = findViewById(R.id.etCurp);
        etNss = findViewById(R.id.etNss);
        etCarrera = findViewById(R.id.etCarrera);
        etGrupo = findViewById(R.id.etGrupo);

        btnSeleccionarFoto = findViewById(R.id.btnSeleccionarFoto);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnConsultarEstado = findViewById(R.id.btnConsultarEstado);

        imgFoto = findViewById(R.id.imgFoto);

        SharedPreferences preferences = getSharedPreferences("sesion", MODE_PRIVATE);
        idUsuario = preferences.getInt("id", 0);

        btnSeleccionarFoto.setOnClickListener(v -> seleccionarFoto());

        btnEnviar.setOnClickListener(v -> enviarDatos());

        btnConsultarEstado.setOnClickListener(v -> {

            Intent intent = new Intent(FormularioCredencialActivity.this, EstadoSolicitudActivity.class);
            startActivity(intent);

        });
    }

    private void seleccionarFoto() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgFoto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    // convertir imagen a Base64
    private String convertirImagenBase64(){

        try{

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);

            byte[] imageBytes = baos.toByteArray();

            return Base64.encodeToString(imageBytes, Base64.DEFAULT);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    private void enviarDatos() {

        String curp = etCurp.getText().toString();
        String nss = etNss.getText().toString();
        String carrera = etCarrera.getText().toString();
        String grupo = etGrupo.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, URL,

                response -> Toast.makeText(this, response, Toast.LENGTH_LONG).show(),

                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()

        ) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("id", String.valueOf(idUsuario));
                params.put("curp", curp);
                params.put("nss", nss);
                params.put("carrera", carrera);
                params.put("grupo", grupo);

                // enviar foto
                if(imageUri != null){
                    String imagenBase64 = convertirImagenBase64();
                    params.put("foto", imagenBase64);
                }

                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

}