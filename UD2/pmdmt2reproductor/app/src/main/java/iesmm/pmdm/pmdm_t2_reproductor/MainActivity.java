package iesmm.pmdm.pmdm_t2_reproductor;

import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LogSistema";
    int score;
    String secuencia_secreta;
    String secuencia_introducida;
    MediaPlayer do1;
    MediaPlayer re;
    MediaPlayer mi;
    MediaPlayer fa;
    MediaPlayer sol;
    MediaPlayer la;
    MediaPlayer si;
    MediaPlayer do2;
    MediaPlayer gasolina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproductor);
        Log.i(TAG,"Se ha iniciado");

        score=0;
        secuencia_secreta ="DoReMiFaSolLaSiDo2";
        secuencia_introducida="";

        Button[] boton= new Button[8];

        do1=MediaPlayer.create(this,R.raw.do1);
        re=MediaPlayer.create(this,R.raw.re);
        mi=MediaPlayer.create(this,R.raw.mi);
        fa=MediaPlayer.create(this,R.raw.fa);
        sol=MediaPlayer.create(this,R.raw.sol);
        la=MediaPlayer.create(this,R.raw.la);
        si=MediaPlayer.create(this,R.raw.si);
        do2=MediaPlayer.create(this,R.raw.do2);
        gasolina=MediaPlayer.create(this,R.raw.gasolina);

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
                    Log.i(TAG,"Se ha pulsado una tecla");
                    Log.i(TAG,"Score: "+score);
                    Toast.makeText(MainActivity.this, "Has pulsado " + (score) + " los botones", Toast.LENGTH_SHORT).show();
                    switch (j){
                        case 0:
                            do1.start();
                            secuencia_introducida=secuencia_introducida+"Do";
                            break;
                        case 1:
                            re.start();
                            secuencia_introducida=secuencia_introducida+"Re";
                            break;
                        case 2:
                            mi.start();
                            secuencia_introducida=secuencia_introducida+"Mi";
                            break;
                        case 3:
                            fa.start();
                            secuencia_introducida=secuencia_introducida+"Fa";
                            break;
                        case 4:
                            sol.start();
                            secuencia_introducida=secuencia_introducida+"Sol";
                            break;
                        case 5:
                            la.start();
                            secuencia_introducida=secuencia_introducida+"La";
                            break;
                        case 6:
                            si.start();
                            secuencia_introducida=secuencia_introducida+"Si";
                            break;
                        case 7:
                            do2.start();
                            secuencia_introducida=secuencia_introducida+"Do2";
                            break;
                    }
                    if (Objects.equals(secuencia_introducida, secuencia_secreta)){
                        Log.i(TAG,"Secuencia correcta");
                        Toast.makeText(MainActivity.this,"Has introducido la secuencia correcta", Toast.LENGTH_SHORT).show();
                        gasolina.start();
                        score=0;
                        secuencia_introducida="";
                    }
                }
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"Se ha reiniciado");
        score=0;
    }
}