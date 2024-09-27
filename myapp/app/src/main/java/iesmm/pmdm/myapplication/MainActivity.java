package iesmm.pmdm.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final String TAG="PMDM";
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
        loadCiccionary();
    }
    private void loadCiccionary(){
        Log.i(TAG,"Idioma"+ Locale.getDefault().getLanguage());

        //Cargar recurso de arrays de strings del diccionario
        Log.i(TAG,this.getResources().getString(R.string.create));

        String words[]=this.getResources().getStringArray(R.array.words);
        for (String word:words) {
            Log.i(TAG, word);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, R.string.stop,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(this, R.string.restart,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, R.string.create,Toast.LENGTH_LONG).show();
    }
}