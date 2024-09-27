package iesmm.pmdm.pmdm_t2_01;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private final String TAG = "PMDM";
    private TextToSpeech sintetizador;

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

        Toast notificacion = Toast.makeText(this, "Se crea la app", Toast.LENGTH_LONG);
        notificacion.show();

        Log.i(TAG, "onCreate"); //Nivel i: info

        //Crear objeto sintetizador
        sintetizador=new TextToSpeech(this, this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart()", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onStart"); //Nivel i: info
        hablar("Empieza la app");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop()", Toast.LENGTH_LONG).show();

        Log.i(TAG, "onStop"); //Nivel i: info
        hablar("Se para la app");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume()", Toast.LENGTH_LONG).show();

        Log.i(TAG, "onResume"); //Nivel i: info
    }



    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause()", Toast.LENGTH_LONG).show();

        Log.i(TAG, "onPause"); //Nivel i: info

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart()", Toast.LENGTH_LONG).show();

        Log.i(TAG, "onRestart"); //Nivel i: info
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_LONG).show();

        Log.i(TAG, "onDestroy"); //Nivel i: info

        sintetizador.shutdown();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(this, "onPostResume()", Toast.LENGTH_LONG).show();

        Log.i(TAG, "onPostResume"); //Nivel i: info
        hablar("Vuelve a la app");
    }

    private void hablar(String msg){
        sintetizador.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
    }
    //Parámetros de configuración del sintetizador
    @Override
    public void onInit(int i) {
        sintetizador.setLanguage(Locale.getDefault()); // Idioma por defecto
        sintetizador.setPitch(1f); //Tono de voz
        sintetizador.setSpeechRate(1f); //Velocidad
    }
}