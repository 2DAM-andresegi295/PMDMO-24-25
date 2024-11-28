package iesmm.pmdm.pmdm_t4_02;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        //Vincular lisener
        Button b = this.findViewById(R.id.boton_iniciar_sesion);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=((EditText) findViewById(R.id.input_usuario)).getText().toString();
                String password=((EditText) findViewById(R.id.input_contrasena)).getText().toString();

                if (getAccess(username,password)){
                    Snackbar.make(view,"Ha accedido", Snackbar.LENGTH_LONG).show();

                    Bundle bundle=new Bundle();
                    bundle.putString("username",username);

                    //Lanzar opcion
                    Intent intent = new Intent(getApplicationContext(), DetaislActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else {
                    Snackbar.make(view,"Login incorrecto", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean getAccess(String username, String password) {
        boolean correcto=false;
        File f=this.getFileStreamPath("usuario.csv");
        if (f.exists()){
            try {
                InputStreamReader in= new InputStreamReader(openFileInput("usuario.csv"));
                BufferedReader bufferedReader = new BufferedReader(in);
                String[] credneciales;
                String linea;
                while ((linea = bufferedReader.readLine()) != null&&!correcto) {
                    credneciales = linea.split(";");
                    if (credneciales[0].equals(username) && credneciales[1].equals(password)) {
                        correcto = true;
                    }
                }
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return correcto;
    }
}