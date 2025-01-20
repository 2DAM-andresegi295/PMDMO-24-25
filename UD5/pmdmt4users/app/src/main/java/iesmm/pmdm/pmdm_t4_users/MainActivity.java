package iesmm.pmdm.pmdm_t4_users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File usuarios = this.getFileStreamPath("users.csv");
        Button btnConfirmar = findViewById(R.id.confirm_button);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usu = findViewById(R.id.usuario);
                EditText pwd = findViewById(R.id.contrasena);
                if (!usu.getText().toString().isEmpty() && !pwd.getText().toString().isEmpty()) {
                    if (usuarios.exists()) {
                        try {
                            BufferedReader bufferedReader = new BufferedReader(new FileReader(usuarios));
                            boolean encontrado = false;
                            String linea;
                            String[] datos;
                            String usuario="";
                            while ((linea = bufferedReader.readLine()) != null && !encontrado) {
                                datos = linea.split(";");
                                if (usu.getText().toString().equals(datos[2]) && pwd.getText().toString().equals(datos[1])) {
                                    encontrado = true;
                                    usuario=datos[2];
                                }
                            }
                            if (encontrado) {
                                Toast.makeText(MainActivity.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                                intent.putExtra("USUARIO_LOGUEADO",usuario);
                                startActivity(intent);
                            } else {
                                Snackbar.make(view,"Email o contraseña incorrectos", Snackbar.LENGTH_LONG).show();
                                /*Toast.makeText(MainActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();*/
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Hay compos vacíos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}