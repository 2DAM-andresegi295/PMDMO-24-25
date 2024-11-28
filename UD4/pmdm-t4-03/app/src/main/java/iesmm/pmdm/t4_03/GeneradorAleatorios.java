package iesmm.pmdm.t4_03;

public class GeneradorAleatorios extends Thread {
    private final int DELAY = 3000; // 3 seconds
    private final int MAX = 10;
    private MainActivity activity;

    public GeneradorAleatorios(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        while (true) {
            // Actualización de la GUI
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int aleatorio = (int) (Math.random() * MAX);
                    activity.actualizarVista(String.valueOf(aleatorio));
                }
            });

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                // Si se produce una interrupción con interrupt() se finaliza la función
                // dado que se ha capturado
                return;
            }
        }
    }
}