package iesmm.pmdm.tresenralla;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    //Representa el estado interno del juego
    //Representa el estado interno del juego
    private JuegoTresEnRaya mJuego;

    private static final String TAG = "LogSistema";

    //Botones del layout
    private Button mBotonesTablero[];

    //Texto inforativo del estado del juego
    private TextView mInfoTexto;
    private TextView scorejugador;
    private TextView scoreMaquina;
    private TextView scorePartidas;

    //Determina quien será primer turno (TURNO INICIAL)
    private char mTurno=JuegoTresEnRaya.JUGADOR;

    //Determina si se ha acabado el juego
    private boolean gameOver=false;

    //Marcadores de partidas, ganadas por jugador y ganadas por máquinas
    private MediaPlayer mJugadorMediaPlayer;
    private MediaPlayer mBackgroundPlayer;

    private int scoJug=0;
    private int scoMac=0;
    private int scoPar=0;

    //Contador de turnos
    int contTur=0;

    Toast toast;
    //Modulador
    private TextToSpeech modulador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoJug =0;
        scoMac =0;
        scoPar =0;

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
        mInfoTexto=findViewById(R.id.informacion);
        scorejugador=findViewById(R.id.player_score);
        scoreMaquina=findViewById(R.id.compter_score);
        scorePartidas=findViewById(R.id.tie_score);


        //Ejecución inicial de la lógica del videojuego
        mJuego=new JuegoTresEnRaya();
        modulador = new TextToSpeech(this, this);
        comenzarJuego();
    }

    private void comenzarJuego(){
        //Reinicio de la lógica del tablero
        mJuego.limpiarTablero();

        //Reinicio de los botones del layout
        for (int i = 0; i < mBotonesTablero.length; i++) {
            mBotonesTablero[i].setText("");
            mBotonesTablero[i].setEnabled(true);
            mBotonesTablero[i].setBackgroundResource(R.drawable.border);

            //FUNCIÓN/OBJETO CALLBACK: Asocio evento al botón para JUGADOR
            //El evento está asociado desde la interfaz del layout XML
        }
        //Turno inicial del juego: JUGADOR O MÁQUINA
        controlarTurno();

    }
    private void controlarTurno(){
        if (mTurno==JuegoTresEnRaya.JUGADOR&&contTur==0) {
            mInfoTexto.setText(R.string.primero_jugador);
        } else if (mTurno == JuegoTresEnRaya.JUGADOR) {
            mInfoTexto.setText(R.string.turno_jugador);
        } else if (mTurno == JuegoTresEnRaya.MAQUINA) {
            //Determinamos la posicion segun nivel
            int casilla=mJuego.getMovimientoMaquina(scoJug);

            //Actualización del layout
            colocarFichaEnElTablero(JuegoTresEnRaya.MAQUINA, casilla);

            //Actualizar turno: Jugador
            if (!gameOver){
                mTurno=JuegoTresEnRaya.JUGADOR;
                //mInfoTexto.setText(R.string.turno_jugador);
            }
        }
        contTur++;
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
            mBotonesTablero[casilla].setBackgroundResource(R.drawable.jugador);
            mJugadorMediaPlayer.start();
        }else {
            mBotonesTablero[casilla].setBackgroundResource(R.drawable.maquina);
        }

        //Se comprueba: ESTADO DEL JUEGO (SI AUN NO SE HA ACABADO SE CONTINUA)
        int estadoJuego=comprobarEstadoJuego();

        if (estadoJuego==1||estadoJuego==2) {
            gameOver();
            contTur = 0;
        }
        else if (estadoJuego == 0) {
            if (jugador==JuegoTresEnRaya.JUGADOR)
                mTurno=JuegoTresEnRaya.MAQUINA;
            else if (jugador == JuegoTresEnRaya.MAQUINA)
                mTurno=JuegoTresEnRaya.JUGADOR;

            controlarTurno();
        }
    }
    private int comprobarEstadoJuego(){
        // 1 Comprobar el estado principal del tablero
        int estado= mJuego.comprobarGanador();
        // 2 representar estado del juego
        if (estado==1){
            toast.makeText(MainActivity.this,R.string.result_human_wins,Toast.LENGTH_SHORT).show();
            modulador.setPitch(1f);
            modulador.speak(getString(R.string.enhorabnuena), TextToSpeech.QUEUE_FLUSH, null);
            scoJug++;
            scorejugador.setText(String.valueOf(scoJug));
            scoPar++;
            scorePartidas.setText(String.valueOf(scoPar));

        }else if (estado==2) {
            toast.makeText(MainActivity.this,R.string.result_computer_wins,Toast.LENGTH_SHORT).show();
            modulador.setPitch(1f);
            modulador.speak(getString(R.string.losiento), TextToSpeech.QUEUE_FLUSH, null);
            scoMac++;
            scoreMaquina.setText(String.valueOf(scoMac));
            scoPar++;
            scorePartidas.setText(String.valueOf(scoPar));
        } else if (estado==3) {
            toast.makeText(MainActivity.this,R.string.result_tie,Toast.LENGTH_SHORT).show();
            scoPar++;
            mInfoTexto.setText(R.string.result_tie);
            scorePartidas.setText(String.valueOf(scoPar));
            estado=1;
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
        comenzarJuego();
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

    public void newGame(View boton){
        //Ctramos una alerta
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        //Ponemos el título
        builder.setTitle(R.string.dialogo_new_game);
        //Ponemos el mensage
        builder.setMessage(R.string.mensage);
        //Si pulsa en el botón positivo resetearemos la partida
        builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resetPartida();
            }
        });
        //Si pulsa el botón negativo no hará nada
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }
    public void resetPartida(){
        scoJug=0;
        scorejugador.setText(String.valueOf(scoJug));
        scoMac=0;
        scoreMaquina.setText(String.valueOf(scoJug));
        scoPar=0;
        scorePartidas.setText(String.valueOf(scoJug));

        comenzarJuego();
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

    @Override
    public void onInit(int i) {

    }
}