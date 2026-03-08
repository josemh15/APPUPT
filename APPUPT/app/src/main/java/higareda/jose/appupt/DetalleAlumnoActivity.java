package higareda.jose.appupt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetalleAlumnoActivity extends AppCompatActivity {

    ImageView imgFoto;
    TextView tvNombre, tvCarrera, tvEstado;
    EditText etComentario;
    Button btnAprobar, btnRechazar;

    String id, nombre, carrera, estado, foto;

    String URL_ACTUALIZAR = "http://192.168.0.108/credenciales/actualizar_estado.php";
    String URL_FOTO = "http://192.168.0.108/credenciales/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_alumno);

        imgFoto = findViewById(R.id.imgFoto);
        tvNombre = findViewById(R.id.tvNombre);
        tvCarrera = findViewById(R.id.tvCarrera);
        tvEstado = findViewById(R.id.tvEstado);
        etComentario = findViewById(R.id.etComentario);

        btnAprobar = findViewById(R.id.btnAprobar);
        btnRechazar = findViewById(R.id.btnRechazar);

        id = getIntent().getStringExtra("id");
        nombre = getIntent().getStringExtra("nombre");
        carrera = getIntent().getStringExtra("carrera");
        estado = getIntent().getStringExtra("estado");
        foto = getIntent().getStringExtra("foto");

        tvNombre.setText(nombre);
        tvCarrera.setText(carrera);
        tvEstado.setText("Estado: " + estado);

        if(foto != null && !foto.isEmpty()){

            Glide.with(this)
                    .load(URL_FOTO + foto)
                    .placeholder(R.drawable.usuario)
                    .into(imgFoto);

        }else{

            imgFoto.setImageResource(R.drawable.usuario);

        }

        btnAprobar.setOnClickListener(v -> actualizarEstado("aprobado"));

        btnRechazar.setOnClickListener(v -> actualizarEstado("rechazado"));
    }

    private void actualizarEstado(String nuevoEstado){

        StringRequest request = new StringRequest(Request.Method.POST, URL_ACTUALIZAR,

                response -> {

                    try{

                        JSONObject json = new JSONObject(response);

                        if(json.getString("status").equals("success")){

                            Toast.makeText(this,"Estado actualizado",Toast.LENGTH_SHORT).show();

                            tvEstado.setText("Estado: " + nuevoEstado);

                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                },

                error -> Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()

        ){

            @Override
            protected Map<String,String> getParams(){

                Map<String,String> params = new HashMap<>();

                params.put("id", id);
                params.put("estado", nuevoEstado);
                params.put("comentario", etComentario.getText().toString());

                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

}