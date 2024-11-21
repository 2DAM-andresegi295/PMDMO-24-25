package iesmm.pmdm.pmdm_t3_access;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    private final String LOGCAT = "PMDM";
    private final String FILENAME = "accesos.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //addTextView(LocalDate.now().toString());
        //addTextView("Hola Mundo");
        //addTextView(LocalDateTime.now().toString());

        //Ruta del fichero: /data/data/iesmm.pmdm.pmdm_t3_access/files/accesos.dat
        saveInFile("Entrada: "+LocalDateTime.now().toString());
        loadFile();
    }

    @Override
    protected void onDestroy() {
        saveInFile("\nSalida: "+LocalDateTime.now().toString());
        super.onDestroy();
    }
    private void loadFile(){
        File f = this.getFileStreamPath(FILENAME);

        if (f.exists()){
            try {
                DataInputStream stream=new DataInputStream(this.openFileInput(FILENAME));

                while (stream.available()!=0){
                    addTextView(stream.readUTF());
                }

                stream.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private void saveInFile(String cad){
        try {
            DataOutputStream data=new DataOutputStream(this.openFileOutput(FILENAME, Context.MODE_APPEND));
            data.writeUTF(cad);
            data.close();

            Log.i(LOGCAT, cad);
        } catch (IOException e) {
            Log.e(LOGCAT,"Error en escritura del fichero");
        }catch (Exception e){
            Log.e(LOGCAT,"Excepción genérica");
        }

    }

    private void addTextView(String cad){
        //1. Localizar el layout para agregarle el componente
        LinearLayout layout = this.findViewById(R.id.container);

        //2. Personalizar TetView
        TextView box=new TextView(this);
        box.setText(cad);
        box.setGravity(Gravity.CENTER);
        if (cad.contains("Entrada")){
            box.setBackgroundColor(Color.GREEN);
        } else if (cad.contains("Salida")) {
            box.setBackgroundColor(Color.RED);
        }

        box.setPadding(10,20,10,20);

        //3. Agregar TextView al layout
        layout.addView(box);
    }


}