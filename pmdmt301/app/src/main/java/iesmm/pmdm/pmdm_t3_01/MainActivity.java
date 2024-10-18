package iesmm.pmdm.pmdm_t3_01;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Asociar eventos a los botones
        Button button=this.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"SAVE", Toast.LENGTH_SHORT).show();
                savePreferences();
            }
        });

        button=this.findViewById(R.id.restore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"RESTORE", Toast.LENGTH_SHORT).show();
                restorePreferences();
            }
        });

        //Cabiar fondo layout
        LinearLayout layout = this.findViewById(R.id.main);
        layout.setBackgroundColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
    }

    private void restorePreferences() {
        SharedPreferences preferencias=this.getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);

        //Recuperar valores de configuración
        String color = preferencias.getString("color",null);

        if (color!=null){
            LinearLayout layout = this.findViewById(R.id.main);
            layout.setBackgroundColor(Integer.valueOf(color));
            Toast.makeText(this,"Color se ha recuperado", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePreferences() {
        SharedPreferences preferencias=this.getSharedPreferences("mispreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        //Almacenar los pares claves-valor
        LinearLayout layout = this.findViewById(R.id.main);
        int color=((ColorDrawable) layout.getBackground()).getColor();
        editor.putInt("color",color);
        editor.putString("color", String.valueOf(color));
        editor.commit();
        Toast.makeText(this,"Cambios almacenados", Toast.LENGTH_SHORT).show();
    }
}