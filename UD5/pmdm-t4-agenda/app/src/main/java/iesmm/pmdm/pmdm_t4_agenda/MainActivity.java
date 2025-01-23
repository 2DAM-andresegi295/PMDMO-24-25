package iesmm.pmdm.pmdm_t4_agenda;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private File contactos;
    private RecyclerView recyclerView;
    private Adaptador adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactos=this.getFileStreamPath("contactos.csv");
        List<Contacto> contactoList = new ArrayList<>();


        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(contactos));
            String linea;
            String[] datos;
            while ((linea = bufferedReader.readLine()) != null) {
                datos=linea.split(";");
                contactoList.add(new Contacto(datos[0],datos[1],datos[2],R.drawable.anonymous));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adaptador=new Adaptador(contactoList, this);
        recyclerView.setAdapter(adaptador);
        Log.d("MainActivity", "Cantidad de contactos: " + contactoList.size());

    }
}