package iesmm.pmdm.pmdm_t3_access;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addTextView(LocalDate.now().toString());
        addTextView("Hola Mundo");
        addTextView(LocalDateTime.now().toString());
    }

    private void addTextView(String cad){
        //1. Localizar el layout para agregarle el componente
        LinearLayout layout = this.findViewById(R.id.container);

        //2. Personalizar TetView
        TextView box=new TextView(this);
        box.setText(cad);
        box.setGravity(Gravity.CENTER);
        box.setBackgroundColor(Color.GREEN);
        box.setPadding(10,20,10,20);

        //3. Agregar TextView al layout
        layout.addView(box);
    }
}