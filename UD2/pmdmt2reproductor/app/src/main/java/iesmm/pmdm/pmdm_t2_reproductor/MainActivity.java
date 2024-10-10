package iesmm.pmdm.pmdm_t2_reproductor;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final String TAG = "LogSistema";

    int score;
    String secuencia_secreta;
    String secuencia_introducida;
    private TextToSpeech modulador;
    private EditText texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproductor);
        Log.i(TAG, "Se ha iniciado");

        score = 0;
        secuencia_secreta = "DoReMiFaSolLaSiDo2";
        secuencia_introducida = "";

        Button[] boton = new Button[8];

        boton[0] = findViewById(R.id.botonDo);
        boton[1] = findViewById(R.id.botonRe);
        boton[2] = findViewById(R.id.botonMi);
        boton[3] = findViewById(R.id.botonFa);
        boton[4] = findViewById(R.id.botonSol);
        boton[5] = findViewById(R.id.botonLa);
        boton[6] = findViewById(R.id.botonSi);
        boton[7] = findViewById(R.id.botonDo2);

        modulador = new TextToSpeech(this, this);

        for (int i = 0; i < boton.length; i++) {
            int j = i;
            boton[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    score++;
                    Log.i(TAG, "Se ha pulsado una tecla");
                    Log.i(TAG, "Score: " + score);
                    Toast.makeText(MainActivity.this, "Has pulsado " + (score) + " botones", Toast.LENGTH_SHORT).show();
                    switch (j) {
                        case 0:
                            modulador.setPitch(1f);
                            modulador.speak("Do", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "Do", Toast.LENGTH_SHORT).show();
                            secuencia_introducida = secuencia_introducida + "Do";
                            break;
                        case 1:
                            modulador.setPitch(1.1f);
                            modulador.speak("Re", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "Re", Toast.LENGTH_SHORT).show();
                            secuencia_introducida = secuencia_introducida + "Re";
                            break;
                        case 2:
                            modulador.setPitch(1.2f);
                            modulador.speak("Mi", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "Mi", Toast.LENGTH_SHORT).show();
                            secuencia_introducida = secuencia_introducida + "Mi";
                            break;
                        case 3:
                            modulador.setPitch(1.3f);
                            modulador.speak("Fa", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "Fa", Toast.LENGTH_SHORT).show();
                            secuencia_introducida = secuencia_introducida + "Fa";
                            break;
                        case 4:
                            modulador.setPitch(1.4f);
                            modulador.speak("Sol", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "Sol", Toast.LENGTH_SHORT).show();
                            secuencia_introducida = secuencia_introducida + "Sol";
                            break;
                        case 5:
                            modulador.setPitch(1.5f);
                            modulador.speak("La", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "La", Toast.LENGTH_SHORT).show();
                            secuencia_introducida = secuencia_introducida + "La";
                            break;
                        case 6:
                            modulador.setPitch(1.6f);
                            modulador.speak("Si", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "Si", Toast.LENGTH_SHORT).show();
                            secuencia_introducida = secuencia_introducida + "Si";
                            break;
                        case 7:
                            modulador.setPitch(1.7f);
                            modulador.speak("Do", TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(MainActivity.this, "Do", Toast.LENGTH_SHORT).show();
                            secuencia_introducida = secuencia_introducida + "Do2";
                            break;
                    }
                    if (Objects.equals(secuencia_introducida, secuencia_secreta)) {
                        Log.i(TAG, "Secuencia correcta");
                        Toast.makeText(MainActivity.this, "Has introducido la secuencia correcta", Toast.LENGTH_SHORT).show();
                        modulador.speak("Has acertado la secuencia secreta", TextToSpeech.QUEUE_FLUSH, null);
                        score = 0;
                        secuencia_introducida = "";
                    }
                }
            });
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Se ha reiniciado");
        score = 0;
    }

    @Override
    public void onInit(int i) {
        modulador.setLanguage(Locale.getDefault());
    }
}