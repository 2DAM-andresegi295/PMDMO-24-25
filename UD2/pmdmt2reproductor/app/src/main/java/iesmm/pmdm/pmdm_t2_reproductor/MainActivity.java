package iesmm.pmdm.pmdm_t2_reproductor;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    int score=0;
    MediaPlayer do1;
    MediaPlayer re;
    MediaPlayer mi;
    MediaPlayer fa;
    MediaPlayer sol;
    MediaPlayer la;
    MediaPlayer si;
    MediaPlayer do2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproductor);
        Button[] boton= new Button[8];

        do1=MediaPlayer.create(this,R.raw.do1);
        re=MediaPlayer.create(this,R.raw.re);
        mi=MediaPlayer.create(this,R.raw.mi);
        fa=MediaPlayer.create(this,R.raw.fa);
        sol=MediaPlayer.create(this,R.raw.sol);
        la=MediaPlayer.create(this,R.raw.la);
        si=MediaPlayer.create(this,R.raw.si);
        do2=MediaPlayer.create(this,R.raw.do2);

        boton[0]=findViewById(R.id.botonDo);
        boton[1]=findViewById(R.id.botonRe);
        boton[2]=findViewById(R.id.botonMi);
        boton[3]=findViewById(R.id.botonFa);
        boton[4]=findViewById(R.id.botonSol);
        boton[5]=findViewById(R.id.botonLa);
        boton[6]=findViewById(R.id.botonSi);
        boton[7]=findViewById(R.id.botonDo2);

        for (int i=0;i<boton.length;i++) {
            int j = i;
            boton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    score++;
                    Toast.makeText(MainActivity.this, "Has pulsado " + (score) + " los botones", Toast.LENGTH_SHORT).show();
                    switch (j){
                        case 0:
                            do1.start();
                            break;
                        case 1:
                            re.start();
                            break;
                        case 2:
                            mi.start();
                            break;
                        case 3:
                            fa.start();
                            break;
                        case 4:
                            sol.start();
                            break;
                        case 5:
                            la.start();
                            break;
                        case 6:
                            si.start();
                            break;
                        case 7:
                            do2.start();
                            break;
                    }
                }
            });
        }



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        score=0;
    }
}