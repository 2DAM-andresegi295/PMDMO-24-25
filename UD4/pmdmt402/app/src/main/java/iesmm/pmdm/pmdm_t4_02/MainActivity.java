package iesmm.pmdm.pmdm_t4_02;

import android.content.Intent;
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
        return true;
    }
}