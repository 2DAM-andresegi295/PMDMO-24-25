package iesmm.pmdm.tresenralla;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class JuegoTresEnRaya {
    // Número de casillas del tablero
    public static final int DIM_TABLERO = 9;

    // Constantes usadas para representar JUGADOR, MÁQUINA, EN BLANCO
    public static final char JUGADOR = 'X';
    public static final char MAQUINA = 'O';
    public static final char BLANCO = ' ';

    // Número aleatorio generado para calcular MAQUINA
    private Random aleatorio;

    // Estructura de dato del tablero
    private char[] tablero;

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

    private void comenzarJuego(){}
    private void completarTurno(){}
    private void colocarFichaEnElTablero(){}
    private int comprobarEstadoJuego(){
        return 0;
    }
    private void gameOver(){}
    private void onClick(View boton){}
    public JuegoTresEnRaya() {
        tablero = new char[DIM_TABLERO];
        aleatorio = new Random();
        limpiarTablero();
    }

    public char[] getTablero() {
        return tablero;
    }

    public void setTablero(char[] tablero) {
        this.tablero = tablero;
    }

    /**
     * Limpia el tablero de todas las X y O
     */
    public void limpiarTablero() {
        // Resetea todos las casillas
        for (int i = 0; i < DIM_TABLERO; i++)
            tablero[i] = BLANCO;
    }

    /**
     * Pone una ficha del tipo en la casilla correspondiente del tablero. La
     * casilla debe estar en BLANCO y dentro de los límites del tablero.
     *
     * @param ficha   - JUGADOR ó MÁQUINA
     * @param casilla - La ubicación de la casilla del tablero (0-[DIM_TABLERO-1])
     *                que se pondrá ficha
     * @return TRUE si el movimiento fue realizado, FALSE en caso contrario.
     */
    public boolean moverFicha(char ficha, int casilla) {
        boolean casilla_ocupada = false;

        if (casilla >= 0 && casilla < DIM_TABLERO && tablero[casilla] == BLANCO) {
            tablero[casilla] = ficha;
            casilla_ocupada = true;
        }

        return casilla_ocupada;
    }

    /**
     * Comprueba si existe un ganador. Devuelve un valor del estado del tablero
     * existente (tipo de ganador, no ganador ó empate), PRIORIZAMOS AL JUGADOR
     *
     * @return Devuelve : * 0 si NO hay ganador * 1 si JUGADOR gana
     * (PRIORIDAD SI SE PRODUCE EMPATE) * 2 si MAQUINA gana
     */
    public int comprobarGanador() {
        // Determina si ha encontrado un valor de estado del tablero
        boolean encontrado = false;
        int valor = 0; // Valor del estado resultante
        int i; // Índice para recorrer el tablero

        // COMPROBACIÓN: LÍNEA HORIZONTAL (FILA)
        i = 0;
        while (i <= 6 && !encontrado) {
            if (tablero[i] == JUGADOR && tablero[i + 1] == JUGADOR
                    && tablero[i + 2] == JUGADOR) {
                valor = 1;
                encontrado = true;
            } else if (tablero[i] == MAQUINA && tablero[i + 1] == MAQUINA
                    && tablero[i + 2] == MAQUINA) {
                valor = 2;
                encontrado = true;
            }

            i += 3;
        }

        // COMPROBACIÓN: LÍNEA VERTICAL (COLUMNA)
        i = 0;
        while (i <= 2 && !encontrado) {
            if (tablero[i] == JUGADOR && tablero[i + 3] == JUGADOR
                    && tablero[i + 6] == JUGADOR) {
                valor = 1;
                encontrado = true;
            } else if (tablero[i] == MAQUINA && tablero[i + 3] == MAQUINA
                    && tablero[i + 6] == MAQUINA) {
                valor = 2;
                encontrado = true;
            }

            i++;
        }

        // COMPROBACIÓN: LÍNEA DIAGONAL (DIAGONAL)
        if ((tablero[0] == JUGADOR && tablero[4] == JUGADOR && tablero[8] == JUGADOR)
                || (tablero[2] == JUGADOR && tablero[4] == JUGADOR && tablero[6] == JUGADOR)) {
            valor = 1;
            encontrado = true;
        } else if ((tablero[0] == MAQUINA && tablero[4] == MAQUINA && tablero[8] == MAQUINA)
                || (tablero[2] == MAQUINA && tablero[4] == MAQUINA && tablero[6] == MAQUINA)) {
            valor = 2;
            encontrado = true;
        }

        // Comprueba NO ganador
        i = 0;
        while (i < DIM_TABLERO && !encontrado) {
            if (tablero[i] == BLANCO) {
                valor = 0;
                encontrado = true;
            }

            i++;
        }

        // Si no hay ganador devolverá 3 para poder diferenciar
        //El turno lo empezará el ganador pero no le contará la partida
        if (!encontrado)
            valor = 3;

        return valor;
    }

    /**
     * Devuelve una casilla para que mueva ficha la MAQUINA.
     *
     * @return El mejor movimiento que puede realizar MAQUINA.
     */
    public int getMovimientoMaquina(int scoJug) {
        int resultado=-1;
        // NIVEL DE DIFICULTAD 1: MOVIMIENTO ALEATORIO
        if (scoJug<2){
            resultado=getMovimientoMaquinaAleatorio();
        } else if (scoJug>=2&&scoJug<4) {
            resultado=getMovimientoFacil();
            if (resultado==-1){
                resultado=getMovimientoMaquinaAleatorio();
            }
        } else if (scoJug>=4&&scoJug<6) {
            resultado=getMovimientoMedio();
            if (resultado==-1){
                resultado=getMovimientoMaquinaAleatorio();
            }
        } else {
            resultado=getMovimientoDificil();
            if (resultado==-1){
                resultado=getMovimientoMaquinaAleatorio();
            }
        }

        return resultado;
    }

    private int getMovimientoMaquinaAleatorio() {
        // Generación de una casilla aleatoria
        int casilla;

        do {
            casilla = aleatorio.nextInt(9);

        } while (tablero[casilla] == JUGADOR || tablero[casilla] == MAQUINA);

        return casilla;
    }
    //Comprobamos si hay posibilidad de que una fila de la victoria a la máquina
    public int getMovimientoFacil(){
        int casilla=-1;
        char[] tableroComprobar;
        int con;
        boolean encontrado;
        for (int i = 0; i < tablero.length; i++) {
            tableroComprobar=tablero.clone();
            con=0;
            encontrado=false;
            if (tableroComprobar[i]==BLANCO){
                tableroComprobar[i]=MAQUINA;
                while (con<7&&!encontrado){
                    if (tableroComprobar[con]==MAQUINA&&tableroComprobar[con+1]==MAQUINA&&tableroComprobar[con+2]==MAQUINA){
                        casilla=i;
                        encontrado=true;
                    }
                    con=con+3;
                }
            }
            if (encontrado){
                break;
            }
        }
        return casilla;
    }
    //Comprobamos si hay posibilidad de que una fila o una columna den la victoria a la máquina
    public int getMovimientoMedio(){
        int casilla=-1;
        char[] tableroComprobar;
        int con;
        boolean encontrado;
        for (int i = 0; i < tablero.length; i++) {
            tableroComprobar=tablero.clone();

            encontrado=false;
            if (tableroComprobar[i]==BLANCO){
                tableroComprobar[i]=MAQUINA;
                con=0;
                while (con<7&&!encontrado){
                    if (tableroComprobar[con]==MAQUINA&&tableroComprobar[con+1]==MAQUINA&&tableroComprobar[con+2]==MAQUINA){
                        casilla=i;
                        encontrado=true;
                    }
                    con=con+3;
                }
                if (encontrado){
                    break;
                }
                con=0;
                while (con<3&&!encontrado){
                    if (tableroComprobar[con]==MAQUINA&&tableroComprobar[con+3]==MAQUINA&&tableroComprobar[con+6]==MAQUINA){
                        casilla=i;
                        encontrado=true;
                    }
                    con++;
                }
            }
            if (encontrado){
                break;
            }
        }
        return casilla;
    }
    //Comprobamos si hay posibilidad de que una fila o una columna o una diagonal den la victoria a la máquina
    public int getMovimientoDificil(){
        int casilla=-1;
        char[] tableroComprobar;
        int con;
        boolean encontrado;
        for (int i = 0; i < tablero.length; i++) {
            tableroComprobar=tablero.clone();

            encontrado=false;
            if (tableroComprobar[i]==BLANCO){
                tableroComprobar[i]=MAQUINA;
                con=0;
                while (con<7&&!encontrado){
                    if (tableroComprobar[con]==MAQUINA&&tableroComprobar[con+1]==MAQUINA&&tableroComprobar[con+2]==MAQUINA){
                        casilla=i;
                        encontrado=true;
                    }
                    con=con+3;
                }
                if (encontrado){
                    break;
                }
                con=0;
                while (con<3&&!encontrado){
                    if (tableroComprobar[con]==MAQUINA&&tableroComprobar[con+3]==MAQUINA&&tableroComprobar[con+6]==MAQUINA){
                        casilla=i;
                        encontrado=true;
                    }
                    con++;
                }
                if (encontrado){
                    break;
                }

                con=0;
                if (tableroComprobar[con]==MAQUINA&&tableroComprobar[con+4]==MAQUINA&&tableroComprobar[con+8]==MAQUINA){
                    casilla=i;
                    encontrado=true;
                }
                if (encontrado){
                    break;
                }
                con=2;
                if (tableroComprobar[con]==MAQUINA&&tableroComprobar[con+2]==MAQUINA&&tableroComprobar[con+4]==MAQUINA){
                    casilla=i;
                    encontrado=true;
                }

            }


        }
        return casilla;
    }


    @Override
    public String toString() {
        return tablero[0] + "|" + tablero[1] + "|" + tablero[2] + "\n"
                + tablero[3] + "|" + tablero[4] + "|" + tablero[5] + "\n"
                + tablero[6] + "|" + tablero[7] + "|" + tablero[8];
    }

    /*public static void main(String[] params) {
        JuegoTresEnRaya juego = new JuegoTresEnRaya();

        // SIMULACIÓN DEL JUGADOR: Hacer diagonal de X (0-4-8)

        // Coloca ficha: JUGADOR
        if (!juego.moverFicha('X', 0))
            System.out.println("CASILLA 0 OCUPADA");

        // Coloca ficha: MÁQUINA
        juego.moverFicha('O', juego.getMovimientoMaquina(0));
        System.out.println("ESTADO: " + juego.comprobarGanador());
        System.out.println("NUEVO TABLERO: \n" + juego + "\n");

        // Coloca ficha: JUGADOR
        if (!juego.moverFicha('X', 4))
            System.out.println("CASILLA 4 OCUPADA");

        // Coloca ficha: MÁQUINA
        juego.moverFicha('O', juego.getMovimientoMaquina(0));
        System.out.println("ESTADO: " + juego.comprobarGanador());
        System.out.println("NUEVO TABLERO: \n" + juego + "\n");

        // Coloca ficha: JUGADOR
        if (!juego.moverFicha('X', 8))
            System.out.println("CASILLA 8 OCUPADA");

        // Coloca ficha: MÁQUINA
        juego.moverFicha('O', juego.getMovimientoMaquina(0));
        System.out.println("ESTADO: " + juego.comprobarGanador());
        System.out.println("NUEVO TABLERO: \n" + juego + "\n");
    }*/
}