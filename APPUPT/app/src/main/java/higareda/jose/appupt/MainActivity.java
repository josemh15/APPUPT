package higareda.jose.appupt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnEstudiante, btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEstudiante = findViewById(R.id.btnEstudiante);
        btnAdmin = findViewById(R.id.btnAdmin);

        btnEstudiante.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginEstudianteActivity.class);
            startActivity(intent);
        });

        btnAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginAdminActivity.class);
            startActivity(intent);
        });
    }
}