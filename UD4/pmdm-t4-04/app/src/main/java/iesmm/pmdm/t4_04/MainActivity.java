package iesmm.pmdm.t4_04;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView numero;
    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numero = this.findViewById(R.id.numero);
        layout = this.findViewById(R.id.layout);

        // Inicio de tarea asíncrona
        GeneradorAleatorios runner = new GeneradorAleatorios();
        runner.execute();
    }

    /*
    Para utilizar las AsyncTasks hay que crear una nueva clase que extienda la clase AsyncTask.

        public class MyTask extends AsyncTask<Params, Progress, Result> {

        La clase AsyncTask se parametriza con tres tipos de datos:

        - Params: El tipo de dato que se pasa como parámetro a la clase, en concreto al método doInBackground.
        - Progress: El tipo de datos utilizado para publicar el avance de la tarea en ejecución en la GUI. Se utiliza en el método onProgressUpdate (en una barra de progreso, por ejemplo).
        - Result: El tipo de datos utilizado para publicar el resultado a la GUI, se transmitirá al método onPostExecute a través del método doInBackground utilizando return.
     */
    private class GeneradorAleatorios extends AsyncTask<Void, Integer, Void> {
        private final String TAG="PMDM";
        private  final  int MAX=100;
        private final int DELAY=3000;
        /*
        onPreExecute:
        Método llamado antes de empezar el procesamiento en segundo plano de doInBackground.
         */
        @Override
        protected void onPreExecute() {
            Log.d(TAG,"Inicio de tarea asíncrona");
        }

        /*
        doInBackground:
        este método se ejecuta en un thread separado, lo que le permite ejecutar un tratamiento pesado en una tarea de segundo plano.
        Recibe como parámetros los declarados al llamar al método execute(Params).
         */
        @Override
        protected Void doInBackground(Void... params) {
            try {
                int n=3;
                while (n>0){
                    //1. Generar aleatorio
                    int aleatorio=(int) (Math.random()*MAX);

                    Log.i(TAG, String.valueOf(aleatorio));
                    //2. Actualizar el datos
                    publishProgress(aleatorio);
                    Thread.sleep(DELAY);
                    n--;
                }
            } catch (InterruptedException e) {
                Log.e(TAG,"Error en la pausa");
            }
            return null;
        }

        /*
        onProgressUpdate:
        es llamado por publishProgress(), dentro de doInBackground(Params) (su uso es muy común para por ejemplo actualizar el porcentaje de un componente ProgressBar).
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG,"Actualizando la vista y el número es "+values[0]);
            numero.setText(String.valueOf(values[0]));

            int RED=(int)(Math.random()*255);
            int GREEN=(int)(Math.random()*255);
            int BLUE=(int)(Math.random()*255);

            layout.setBackgroundColor(Color.rgb(RED, GREEN, BLUE));
        }

        /*
        Este método es llamado tras finalizar doInBackground(Params).
        Recibe como parámetro el resultado devuelto por doInBackground(Params).
         */
        @Override
        protected void onPostExecute(Void param) {
            Log.d(TAG,"Fin de la tarea asíncrona");
        }
    }
}