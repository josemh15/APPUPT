package higareda.jose.appupt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistroCuentaActivity extends AppCompatActivity {

    EditText etNombre, etCorreo, etPassword;
    Button btnRegistrar;

    String URL = "http://192.168.0.108/credenciales/registro_estudiante.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cuenta);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {

        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(nombre.isEmpty() || correo.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Completa todos los campos",Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, URL,

                response -> {

                    if(response.equals("registro_exitoso")){

                        Toast.makeText(this,"Cuenta creada correctamente",Toast.LENGTH_LONG).show();
                        finish();

                    }else{

                        Toast.makeText(this,"Error al registrar",Toast.LENGTH_LONG).show();

                    }

                },

                error -> Toast.makeText(this,"Error: "+error.toString(),Toast.LENGTH_LONG).show()

        ){

            @Override
            protected Map<String, String> getParams(){

                Map<String,String> params = new HashMap<>();

                params.put("nombre", nombre);
                params.put("correo", correo);
                params.put("password", password);

                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}