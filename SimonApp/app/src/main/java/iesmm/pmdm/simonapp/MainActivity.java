package iesmm.pmdm.simonapp;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView score;
    private int pulsaciones;
    private final Random random = new Random();
    private Button boton1;
    private Button boton2;
    private Button boton3;
    private Button boton4;
    private ArrayList<Integer> patron = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = findViewById(R.id.marcador);
        boton1 = findViewById(R.id.boton1);
        boton2 = findViewById(R.id.boton2);
        boton3 = findViewById(R.id.boton3);
        boton4 = findViewById(R.id.boton4);

        boton1.setOnClickListener(v -> revisarBoton(1));
        boton2.setOnClickListener(v -> revisarBoton(2));
        boton3.setOnClickListener(v -> revisarBoton(3));
        boton4.setOnClickListener(v -> revisarBoton(4));

        empezarRonda();
    }

    private void empezarRonda() {
        patron.add(random.nextInt(4) + 1);
        pulsaciones = 0;
        new DibujarPatronTask().execute();
    }

    private class DibujarPatronTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < patron.size(); i++) {
                publishProgress(patron.get(i));  // Resalta el botón actual
                try {
                    Thread.sleep(1000); // Esperar entre cada resaltado (duración entre botones)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int boton = values[0];
            resaltarBoton(boton);
        }
    }

    private void resaltarBoton(int boton) {
        for (int i = 1; i <= 4; i++) {
            Button button = getBoton(i);
            if (button != null) {
                if (i == boton) {
                    button.setAlpha(1.0f); // Resaltar el botón seleccionado
                } else {
                    button.setAlpha(0.1f); // Oscurecer los demás
                }
            }
        }

        boton1.postDelayed(() -> {
            for (int i = 1; i <= 4; i++) {
                Button button = getBoton(i);
                if (button != null) {
                    button.setAlpha(1.0f); // Restaurar todos los botones a su estado original
                }
            }
        }, 1500);  // Tiempo de resalto de cada botón aumentado a 1500 ms
    }

    private Button getBoton(int boton) {
        switch (boton) {
            case 1:
                return boton1;
            case 2:
                return boton2;
            case 3:
                return boton3;
            case 4:
                return boton4;
            default:
                return null;
        }
    }

    private void revisarBoton(int boton) {
        if (boton == patron.get(pulsaciones)) {
            pulsaciones++;
            if (pulsaciones == patron.size()) {
                Toast.makeText(this, "¡Correcto! Nueva ronda", Toast.LENGTH_SHORT).show();
                score.setText("Marcador: " + patron.size());
                empezarRonda();
            }
        } else {
            Toast.makeText(this, "¡Error! Inténtalo de nuevo", Toast.LENGTH_SHORT).show();
            patron.clear();
            score.setText("Marcador: " + patron.size());
            empezarRonda();
        }
    }
}