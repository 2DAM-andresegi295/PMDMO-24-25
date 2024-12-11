package iesmm.pmdm.brujulapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.BitmapKt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private final String TAG = "PMDM";
    private ImageView imageView;
    //Posición original de la brújula
    private Bitmap originalBitmap;
    private boolean corriendo=true;
    private float orientacion;
    File mediciones;
    FileWriter fileWriter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        imageView=findViewById(R.id.brujula);
        originalBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.brujula);
        mediciones=this.getFileStreamPath("mediciones.csv");
        if (mediciones.exists()){
            mediciones.delete();
        }
        try {
            mediciones.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (sensor!=null){
            sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
            new Medidor().execute();
        }else {
            Toast.makeText(this, "No existe sensor",Toast.LENGTH_SHORT).show();
            Log.e(TAG,"No existe el sensor");
        }

    }
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
        corriendo = false;
        Log.e(TAG,"Se para el sensor y la tarea");

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType()==Sensor.TYPE_ORIENTATION){

            orientacion=sensorEvent.values[0];

            orientacion=(float) Math.toDegrees(orientacion);
            orientacion=(orientacion+360)%360;


            Log.i(TAG, "Orientacion: "+orientacion);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private Bitmap rotarBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle); // Rotar la matriz en el ángulo dado
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    private class Medidor extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            while (corriendo){
                publishProgress();
                try {
                    Thread.sleep(1000);
                    Log.i("TAREA", "Dormido");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Bitmap nuevoBitmap= rotarBitmap(originalBitmap,((float) orientacion) );
            TextView textViewGrados=findViewById(R.id.grados);
            textViewGrados.setText(String.format("%.3f",orientacion));

            imageView.setImageBitmap(nuevoBitmap);
            String punto="";
            if (orientacion >= 0 && orientacion < 45) {
                punto="Norte (N)";
            } else if (orientacion >= 45 && orientacion < 90) {
                punto="Noreste (NE)";
            } else if (orientacion >= 90 && orientacion < 135) {
                punto="Este (E)";
            } else if (orientacion >= 135 && orientacion < 180) {
                punto="Sureste (SE)";
            } else if (orientacion >= 180 && orientacion < 225) {
                punto="Sur (S)";
            } else if (orientacion >= 225 && orientacion < 270) {
                punto="Suroeste (SW)";
            } else if (orientacion >= 270 && orientacion < 315) {
                punto="Oeste (W)";
            }
            TextView textViewDireccion=findViewById(R.id.direccion);
            textViewDireccion.setText(punto);

            Calendar calendar = Calendar.getInstance();

            try {
                fileWriter=new FileWriter(mediciones, true);
                fileWriter.write(punto+"@"+ calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND)+"\n");
                fileWriter.close();
                Log.i("TAREA" ,punto+"@"+ calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }
}