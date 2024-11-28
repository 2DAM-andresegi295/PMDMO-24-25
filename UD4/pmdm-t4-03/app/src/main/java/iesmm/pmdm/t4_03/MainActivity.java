package iesmm.pmdm.t4_03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GeneradorAleatorios hilo;
    private Button start;
    private Button stop;
    private int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 1. Instanciación de componentes visuales para su control
        start = (Button) this.findViewById(R.id.start);
        stop = (Button) this.findViewById(R.id.stop);

        // 2. Vinculamos el control del escuchador de eventos con el componente
        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        // OPCIÓN ALTERNATIVA: Un escuchador por cada componente visual
        /**
         * IMPLEMENTACIÓN DEL MANEJADOR/ESCUCHADOR DEL BOTÓN DE EVENTO TIPO ACTION POR
         * INSTANCIA DE UNA CLASE ANÓNIMA (PRIVADAS, INTERIORES Y ANIDADAS)
         *
         * Las clases anónimas son clases que, además de ser internas, se declaran con
         * una sintaxis especial. Se llaman anónimas porque no se las da nombre al
         * declararlas y, puesto que no tienen nombre, no se pueden usar variables ni
         * atributos de estas clases. Por último, su característica más llamativa es que
         * se declaran en la misma sentencia en la que se instancia un objeto de las
         * mismas.
         *
         * VENTAJAS: No hace falta diferenciar el origen del componente del evento
         * producido Y no obliga a implementar una clase separada de la principal (a
         * pesar de estar anidada)
         *
         * INCONVENIENTES: Complejidad del código principal, no se pueden usar variables
         * ni atributos fuera de la definición explícita
         *
         * @author Alejandro Cardo Grau
         */
        /*
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });*/
    }

    public void start() {
        hilo = new GeneradorAleatorios(this);
        hilo.start();
    }

    public void stop() {
        hilo.interrupt();
        actualizarVista(this.getString(R.string.off));
    }

    /* Método síncrono de control para cada uno de los hilos que actualice la vista */
    public synchronized void actualizarVista(String msg) {
        TextView aleatorio = (TextView) this.findViewById(R.id.resultado);
        TextView nveces = (TextView) this.findViewById(R.id.nveces);
        n++;

        aleatorio.setText(msg);
        nveces.setText("Número de aleatorios generados: " + n);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                start();
                break;

            case R.id.stop:
                stop();
                break;
        }
    }
}