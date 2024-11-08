package iesmm.pmdm.pmdm_t3_listview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cargar datos
        loadData();
    }
    public void loadData(){
        ArrayList lista=new ArrayList();

        for (int i = 0; i < 40; i++) {
            lista.add("Item "+i);
        }
        addItemsInlistView(lista);
    }
    private void addItemsInlistView(ArrayList datos){
        //1. Localizar listview
        ListView lista = this.findViewById(R.id.listView1);

        //2. Vincular listview al d¡modelo de datos, a través del adaptador
        adaptador=new ArrayAdapter(this, android.R.layout.simple_list_item_1, datos);
        lista.setAdapter(adaptador);

        //3. Asociar un evento a cada item
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cadena="Posición: "+i+
                        "\nNº Total del items: "+adapterView.getCount()+
                        "\n Item: "+adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), cadena,Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void putItem(View view){
        //Localizar el texto del edittex
        EditText text=this.findViewById(R.id.editText);

        if (!text.getText().toString().isEmpty()){
            adaptador.add(text.getText().toString());
        }
        else {
            Toast.makeText(this,"No hay nada que insertar", Toast.LENGTH_SHORT).show();
        }
    }
    public void clearItems(View view){
        if (adaptador!=null){
            adaptador.clear();
        }
    }
}