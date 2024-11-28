package iesmm.pmdm.t4_06;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private final String LOGTAG = "PMDM";
    private TextView buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buffer = this.findViewById(R.id.buffer);

        if (disponibleInterfazConexion()) {
            // Ejecución de tarea asíncrona de descarga
            // new MyTask().execute("https://iesmartinezm.es");
            new MyTaskJSON().execute("https://random-data-api.com/api/address/random_address");
        } else
            Toast.makeText(this, "No disponible interfaz", Toast.LENGTH_LONG).show();
    }

    private boolean disponibleInterfazConexion() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private String readURL(String direccion) {
        String lines = "";

        try {
            URL url = new URL(direccion);
            URLConnection urlConexion = url.openConnection();

            // Obtención del flujo
            InputStream inputstream = urlConexion.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inputstream));

            // Lectura del flujo
            String line = "";
            while ((line = input.readLine()) != null)
                lines += line + "\n";

            // Cierre
            inputstream.close();
            input.close();
        } catch (MalformedURLException e) {
            Log.d(LOGTAG, "URL mal definida");
        } catch (IOException e) {
            Log.d(LOGTAG, e.getMessage());
        } catch (Exception e) {
            Log.d(LOGTAG, e.getMessage());
        }

        return lines;
    }

    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            Log.d(LOGTAG, "onPreExecute()");
            buffer.setText("Leyendo ...");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(LOGTAG, strings[0]);
            return readURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String cad) {
            Log.d(LOGTAG, "Leida URL: " + cad);

            if (!cad.isEmpty())
                buffer.setText(cad);
            else
                buffer.setText("Error en conexión");
        }
    }

    /* Formato JSON de: https://random-data-api.com/api/address/random_address

    {
      "id": 8578,
      "uid": "fe9b350b-986b-446d-8d13-5fca1c1b3efd",
      "city": "Brekketon",
      "street_name": "Crooks Expressway",
      "street_address": "97412 Dotty Shore"
    }

    */
    private class MyTaskJSON extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            Log.d(LOGTAG, "onPreExecute()");
            buffer.setText("Leyendo ...");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(LOGTAG, strings[0]);
            return readURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String cad) {
            Log.d(LOGTAG, "Leida URL: " + cad);

            if (!cad.isEmpty()) {
                try {
                    // Lectura del flujo y serialización a formato JSON
                    JSONObject obj = new JSONObject(cad);
                    buffer.setText(obj.getString("city"));
                } catch (JSONException e) {
                    buffer.setText("Error en serialización");
                }
            } else
                buffer.setText("Error en conexión");
        }
    }
}