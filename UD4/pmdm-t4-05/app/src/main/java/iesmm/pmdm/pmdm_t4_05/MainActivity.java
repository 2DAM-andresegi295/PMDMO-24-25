package iesmm.pmdm.pmdm_t4_05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Instanciación de componentes visuales para su control
        Button start = (Button) this.findViewById(R.id.button);

        // 2. Vinculamos el control del escuchador de eventos con el componente
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                showProgress();
                break;
        }
    }

    /* Muestra un cuadro de diálogo con barra de progreso */
    private void showProgress() {
        Descarga descarga=new Descarga();
        descarga.execute();

        progress=new ProgressDialog(this);
        progress.setTitle("Descarga");
        progress.setMessage("Descargando...");

        progress.setMax(100);
        progress.setCancelable(true);

        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.show();

        progress.getProgress();


    }
    private class Descarga extends AsyncTask<Void,Integer,Void>{
        private final String TAG = "PMDM";
        private final int MAXIMO = 100;
        private final int RETARDO = 1 * 1000;


        @Override
        protected Void doInBackground(Void... params) {
            try {
                int contador = 0;
                int icremento = 1;

                while (contador < MAXIMO) {
                    publishProgress(icremento);
                    contador += icremento;

                    Thread.sleep(RETARDO);
                }
            } catch (InterruptedException e) {
                Log.e(TAG, "Error en el incremento");
            } return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.incrementProgressBy(values[0]);
        }
    }
}