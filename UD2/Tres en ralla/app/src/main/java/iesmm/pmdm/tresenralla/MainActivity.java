package iesmm.pmdm.tresenralla;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //Representa el estado interno del juego
    //Representa el estado interno del juego
    private JuegoTresEnRaya mJuego;

    //Botones del layout
    private Button mBotonesTablero[];

    //Texto inforativo del estado del juego
    private TextView mInfoTexto;

    //Determina quien será primer turno (TURNO INICIAL)
    private char mTurno=JuegoTresEnRaya.JUGADOR;

    //Determina si se ha acabado el juego
    private boolean gameOver=false;

    private MediaPlayer mJugadorMediaPlayer;
    private MediaPlayer mBackgroundPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencia de los botones del tablero
        mBotonesTablero= new Button[JuegoTresEnRaya.DIM_TABLERO];
        mBotonesTablero[0]=(Button) findViewById(R.id.one);
        mBotonesTablero[1]=(Button) findViewById(R.id.two);
        mBotonesTablero[2]=(Button) findViewById(R.id.three);
        mBotonesTablero[3]=(Button) findViewById(R.id.four);
        mBotonesTablero[4]=(Button) findViewById(R.id.five);
        mBotonesTablero[5]=(Button) findViewById(R.id.six);
        mBotonesTablero[6]=(Button) findViewById(R.id.seven);
        mBotonesTablero[7]=(Button) findViewById(R.id.eight);
        mBotonesTablero[8]=(Button) findViewById(R.id.nine);

        //Referencia de los textos informativos del estado del juego
        mInfoTexto=(TextView) findViewById(R.id.informacion);


        //Ejecución inicial de la lógica del videojuego
        mJuego=new JuegoTresEnRaya();
        comenzarJuego();

    }


    private void comenzarJuego(){
        //Reinicio de la lógica del tablero
        mJuego.limpiarTablero();

        //Reinicio de los botones del layout
        for (int i = 0; i < mBotonesTablero.length; i++) {
            mBotonesTablero[i].setText("");
            mBotonesTablero[i].setEnabled(true);
            //FUNCIÓN/OBJETO CALLBACK: Asocio evento al botón para JUGADOR
            //El evento está asociado desde la interfaz del layout XML
        }

        //Turno iicial del juego: JUGADOR O MÁQUINA
        controlarTurno();

    }
    private void controlarTurno(){
        if (mTurno==JuegoTresEnRaya.JUGADOR)
            mInfoTexto.setText(R.string.primero_jugador);

         else if (mTurno == JuegoTresEnRaya.MAQUINA) {
            //Determinamos la posicion segun nivel
            int casilla=mJuego.getMovimientoMaquina();

            //Actualización del layout
            colocarFichaEnElTablero(JuegoTresEnRaya.MAQUINA, casilla);

            //Actualizar turno: Jugador
            if (!gameOver){
                mTurno=JuegoTresEnRaya.JUGADOR;
                mInfoTexto.setText(R.string.turno_jugador);
            }
        }
    }
    private void colocarFichaEnElTablero(char jugador, int casilla){
        //Mueve la ficha según lógica
        mJuego.moverFicha(jugador, casilla);

        //Actualizacion representacion en el layout
        //Se desactiva el control del boton determinado

        mBotonesTablero[casilla].setEnabled(false);
        mBotonesTablero[casilla].setText(String.valueOf(jugador));

        //Se representa la ficha
        if (jugador==JuegoTresEnRaya.JUGADOR){
            //mBotonesTablero[casilla].setTextColor(Color.rgb(0,200,0));
            mBotonesTablero[casilla].setBackgroundResource(R.drawable.jugador);
            mJugadorMediaPlayer.start();

        }else {
            //mBotonesTablero[casilla].setTextColor(Color.rgb(200,0,0));
            mBotonesTablero[casilla].setBackgroundResource(R.drawable.maquina);
        }

        //Se comprueba: ESTADO DEL JUEGO (SI AUN NO SE HA ACABADO SE CONTINUA)
        int estadoJuego=comprobarEstadoJuego();

        if (estadoJuego==1||estadoJuego==2){
            gameOver();
        } else if (estadoJuego == 0) {
            if (jugador==JuegoTresEnRaya.JUGADOR){
                mTurno=JuegoTresEnRaya.MAQUINA;
            } else if (jugador == JuegoTresEnRaya.MAQUINA) {
                mTurno=JuegoTresEnRaya.JUGADOR;
            }
        }
        controlarTurno();
    }
    private int comprobarEstadoJuego(){
        // 1 Comprobar el estado principal del tablero
        int estado= mJuego.comprobarGanador();

        // 2 representar estado del juego
        if (estado==1){
            mInfoTexto.setText(R.string.result_human_wins);
        } else if (estado==2) {
            mInfoTexto.setText(R.string.result_computer_wins);
        }

        return estado;
    }
    private void gameOver(){
        //Actualizo la variablle del control de la finalización del juego
        gameOver=true;
        //Reinicio de los botones del layout a desactivados
        for (int i = 0; i < mBotonesTablero.length; i++) {
            mBotonesTablero[i].setEnabled(false);
        }
    }

    public void onClick(View boton){
        //Localizamos cual es el botón pulsado y su número de casilla
        int id=boton.getId();
        String descripcionBoton=((Button) findViewById(id)).getContentDescription().toString();
        int casilla=Integer.parseInt(descripcionBoton)-1;

        //Determinamos si es posible colocar la ficha en la casilla
        if (mBotonesTablero[casilla].isEnabled()){
            //Mueve y representa la ficha jugador en la casilla
            colocarFichaEnElTablero(JuegoTresEnRaya.JUGADOR, casilla);
        }
    }
    @Override
    protected void onResume(){
        super.onResume();

        mJugadorMediaPlayer=MediaPlayer.create(this,R.raw.effect);
        mBackgroundPlayer=MediaPlayer.create(this,R.raw.backgound_music);

        mBackgroundPlayer.setLooping(true);
        mBackgroundPlayer.start();
    }
    @Override
    protected void onPause(){
        super.onPause();

        mJugadorMediaPlayer.release();
        mBackgroundPlayer.release();
    }

}