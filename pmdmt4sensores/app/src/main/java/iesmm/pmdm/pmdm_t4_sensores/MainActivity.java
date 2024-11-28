package iesmm.pmdm.pmdm_t4_sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private Sensor luminosidad;
    private final String TAG = "PMDM";
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Obtener la disponibilidad del vibrador
        vibrator=(Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        //Obtener la disponibilidad del sensor
        sensorManager=(SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        luminosidad=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (sensor!=null){
            //Registrar escucha de eventos
            sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this,luminosidad, SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this, "No existe sensor",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType()==Sensor.TYPE_PROXIMITY){
            Log.i(TAG, "Media obtenida proximidad: "   + sensorEvent.values[0]);
            if (sensorEvent.values[0]<5){
                Toast.makeText(this,"Aleja el dispositivo", Toast.LENGTH_SHORT).show();

                vibrator.vibrate(2000);

                //Patron de vibración
                //1º Tiempo previo en el que empieza la secuencia
                //2º Valor: vibra 0,5 segundos
                //3º valor: apagar
                //4º Valor: vibra 2000 mili

                long patron[]={0,500,0,2000};
                vibrator.vibrate(patron,1);
            }
        } else if (sensorEvent.sensor.getType()==Sensor.TYPE_LIGHT) {
            Log.i(TAG, "Media obtenida luz: "   + sensorEvent.values[0]);
        }


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    protected void onPostResume(){
        super.onPostResume();
        sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}