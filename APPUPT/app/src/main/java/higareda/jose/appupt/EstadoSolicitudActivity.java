package higareda.jose.appupt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class EstadoSolicitudActivity extends AppCompatActivity {

    TextView tvNombre, tvCarrera, tvEstado;
    TextView tvMatricula, tvComentario;

    String idUsuario;

    String URL = "http://192.168.0.108/credenciales/estado_solicitud.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_solicitud);

        tvNombre = findViewById(R.id.tvNombre);
        tvCarrera = findViewById(R.id.tvCarrera);
        tvEstado = findViewById(R.id.tvEstado);
        tvMatricula = findViewById(R.id.tvMatricula);
        tvComentario = findViewById(R.id.tvComentario);

        SharedPreferences preferences = getSharedPreferences("sesion", MODE_PRIVATE);
        idUsuario = String.valueOf(preferences.getInt("id", 0));

        cargarEstado();
    }

    private void cargarEstado(){

        StringRequest request = new StringRequest(Request.Method.POST, URL,

                response -> {

                    try{

                        JSONObject json = new JSONObject(response);

                        String nombre = json.getString("nombre");
                        String carrera = json.getString("carrera");
                        String estado = json.getString("estado");
                        String matricula = json.optString("matricula");
                        String mensaje = json.optString("mensaje");

                        tvNombre.setText("Nombre: " + nombre);
                        tvCarrera.setText("Carrera: " + carrera);
                        tvEstado.setText("Estado: " + estado);

                        // ESTADO PENDIENTE
                        if(estado.equalsIgnoreCase("PENDIENTE")){

                            tvMatricula.setText("Matrícula: En proceso");
                            tvComentario.setText("Comentario: Tu solicitud está en revisión");

                        }

                        // ESTADO APROBADO
                        else if(estado.equalsIgnoreCase("aprobado")){

                            tvMatricula.setText("Matrícula: " + matricula);
                            tvComentario.setText("Comentario: Credencial aprobada");

                        }

                        // ESTADO RECHAZADO
                        else if(estado.equalsIgnoreCase("rechazado")){

                            tvMatricula.setText("Matrícula: No asignada");
                            tvComentario.setText("Comentario: " + mensaje);

                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                },

                error -> error.printStackTrace()

        ){

            @Override
            protected java.util.Map<String,String> getParams(){

                java.util.Map<String,String> params = new java.util.HashMap<>();

                params.put("id_usuario", idUsuario);

                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}