package higareda.jose.appupt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginEstudianteActivity extends AppCompatActivity {

    EditText etCorreo, etPassword;
    Button btnLogin, btnRegistro;

    String URL = "http://192.168.0.108/credenciales/login_estudiante.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_estudiante);

        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnLogin.setOnClickListener(v -> login());

        btnRegistro.setOnClickListener(v -> {

            Intent i = new Intent(this, RegistroCuentaActivity.class);
            startActivity(i);

        });

    }

    private void login() {

        String correo = etCorreo.getText().toString();
        String password = etPassword.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, URL,

                response -> {

                    try {

                        JSONObject json = new JSONObject(response);

                        if (json.getString("status").equals("success")) {

                            int id = json.getInt("id");
                            String nombre = json.getString("nombre");

                            guardarSesion(id, nombre);

                            Toast.makeText(this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(this, FormularioCredencialActivity.class);
                            startActivity(i);

                            finish();

                        } else {

                            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                },

                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()

        ) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("correo", correo);
                params.put("password", password);

                return params;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void guardarSesion(int id, String nombre) {

        SharedPreferences preferences = getSharedPreferences("sesion", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("id", id);
        editor.putString("nombre", nombre);

        editor.apply();

    }

}