package iesmm.pmdm.pmdm_t4_02;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetaislActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detaisl);

        //Recuperar datos bundle
        Bundle parametros=this.getIntent().getExtras();
        if (parametros!=null){
            String username=parametros.getString("username");
            TextView t=this.findViewById(R.id.welcome);
            t.setText("Bienvenido\n"+username);
        }
    }
}