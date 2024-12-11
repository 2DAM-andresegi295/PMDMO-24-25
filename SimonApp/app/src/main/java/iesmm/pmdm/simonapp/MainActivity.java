package iesmm.pmdm.simonapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextView score;
    private int pulsaciones;
    private final Random random = new Random();
    private Button boton1;
    private Button boton2;
    private Button boton3;
    private Button boton4;
    private ArrayList<Integer> patron = new ArrayList<>();
    private Vibrator vibrator;
    private TextToSpeech modulador;
    private int retraso=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = findViewById(R.id.marcador);
        score.setText(getString(R.string.marcador)+" 0");

        vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);

        modulador = new TextToSpeech( this, this);

        boton1 = findViewById(R.id.boton1);
        boton2 = findViewById(R.id.boton2);
        boton3 = findViewById(R.id.boton3);
        boton4 = findViewById(R.id.boton4);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revisarBoton(1);
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revisarBoton(2);
            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revisarBoton(3);
            }
        });

        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revisarBoton(4);
            }
        });


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
            //Añadimos dificultad
            if (patron.size()>5){
                retraso=500;
            }
            for (int i = 0; i < patron.size(); i++) {
                try {
                    Thread.sleep(retraso);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(patron.get(i));
                try {
                    Thread.sleep(retraso);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int boton = values[0];
            vibrator.vibrate(500); //Vibrará amtes de cada animación
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

        boton1.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 4; i++) {
                    Button button = getBoton(i);
                    if (button != null) {
                        button.setAlpha(1.0f); // Restaurar todos los botones a su estado original
                    }
                }
            }
        }, 1500);  // Tiempo de resalto de cada botón
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
                modulador.speak(getString(R.string.correcto), TextToSpeech.QUEUE_FLUSH, null);
                Toast.makeText(this, getString(R.string.correcto) , Toast.LENGTH_SHORT).show();
                score.setText(getString(R.string.marcador) +" "+ patron.size());
                empezarRonda();
            }
        } else {
            modulador.speak(getString(R.string.incorrecto), TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(this,getString(R.string.incorrecto) , Toast.LENGTH_SHORT).show();
            patron.clear();
            score.setText(getString(R.string.marcador) +" "+ patron.size());
            empezarRonda();
        }
    }
    @Override
    public void onInit(int i) {
        modulador.setLanguage(Locale.getDefault());
    }
}