package higareda.jose.appupt;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminDashboardActivity extends AppCompatActivity {

    RecyclerView recycler;
    ArrayList<Estudiante> lista;
    EstudianteAdapter adapter;

    String URL = "http://192.168.0.108/credenciales/listar_estudiantes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        recycler = findViewById(R.id.recyclerEstudiantes);

        lista = new ArrayList<>();
        adapter = new EstudianteAdapter(lista);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        cargarEstudiantes();
    }

    private void cargarEstudiantes(){

        StringRequest request = new StringRequest(Request.Method.GET, URL,

                response -> {

                    try {

                        JSONArray jsonArray = new JSONArray(response);

                        lista.clear();

                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject obj = jsonArray.getJSONObject(i);

                            String id = obj.getString("id");
                            String nombre = obj.getString("nombre");
                            String carrera = obj.getString("carrera");
                            String estado = obj.getString("estado");

                            String foto = obj.optString("foto","");

                            lista.add(new Estudiante(
                                    id,
                                    nombre,
                                    carrera,
                                    estado,
                                    foto
                            ));
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this,"Error procesando datos",Toast.LENGTH_LONG).show();
                    }

                },

                error -> Toast.makeText(this,"Error conexión: "+error.toString(),Toast.LENGTH_LONG).show()

        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}