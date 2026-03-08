package higareda.jose.appupt;

import android.content.Intent;
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

public class LoginAdminActivity extends AppCompatActivity {

    EditText etUsuario, etPassword;
    Button btnLoginAdmin;

    String URL = "http://192.168.0.108/credenciales/login_admin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnLoginAdmin = findViewById(R.id.btnLoginAdmin);

        btnLoginAdmin.setOnClickListener(v -> loginAdmin());
    }

    private void loginAdmin(){

        String usuario = etUsuario.getText().toString();
        String password = etPassword.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, URL,

                response -> {

                    try{

                        JSONObject json = new JSONObject(response);

                        if(json.getString("status").equals("success")){

                            Toast.makeText(this,"Bienvenido administrador",Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(this, AdminDashboardActivity.class);
                            startActivity(i);

                            finish();

                        }else{

                            Toast.makeText(this,"Datos incorrectos",Toast.LENGTH_SHORT).show();

                        }

                    }catch(Exception e){
                        Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                },

                error -> Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()

        ){

            @Override
            protected Map<String,String> getParams(){

                Map<String,String> params = new HashMap<>();

                params.put("usuario",usuario);
                params.put("password",password);

                return params;

            }

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

}