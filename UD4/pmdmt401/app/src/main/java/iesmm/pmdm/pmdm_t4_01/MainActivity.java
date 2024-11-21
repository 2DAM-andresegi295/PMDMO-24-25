package iesmm.pmdm.pmdm_t4_01;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import iesmm.pmdm.pmdm_t4_01.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements GestorAplicacion{

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private final String LOGCAT="PMDM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.create_action_navegador_web) {
            Log.i(LOGCAT, "create_action_navegador_web");
            Toast.makeText(this, "create_action_navegador_web", Toast.LENGTH_SHORT).show();
            cargarNavegadorWeb("https://www.futbin.com");
        } else if (id==R.id.create_action_marcador_llamada) {
            Log.i(LOGCAT, "create_action_marcador_llamada");
            Toast.makeText(this, "create_action_marcador_llamada", Toast.LENGTH_SHORT).show();
            abrirMarcadorLlamada();
        } else if (id == R.id.create_action_llamada_numero) {
            Log.i(LOGCAT, "create_action_llamada_numero");
            Toast.makeText(this, "create_action_llamada_numero", Toast.LENGTH_SHORT).show();
            marcarLlamada("123");
        } else if (id == R.id.create_action_llamada) {
            Log.i(LOGCAT, "create_action_llamada");
            Toast.makeText(this, "create_action_llamada", Toast.LENGTH_SHORT).show();
            realizarLlamada("123");
        } else if (id == R.id.create_action_mensaje_sms) {
            Log.i(LOGCAT, "create_action_mensaje_sms");
            Toast.makeText(this, "create_action_mensaje_sms", Toast.LENGTH_SHORT).show();
            mandarMensaje("123");
        } else if (id == R.id.create_action_mensaje_compartir) {
            Log.i(LOGCAT, "create_action_mensaje_compartir");
            Toast.makeText(this, "create_action_mensaje_compartir", Toast.LENGTH_SHORT).show();
            mandarMensaje("123","Hola");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void cargarNavegadorWeb(String url) {
        Uri uri=Uri.parse(url);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        this.startActivity(intent);
    }

    @Override
    public void abrirMarcadorLlamada() {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        this.startActivity(intent);
    }

    @Override
    public void marcarLlamada(String telefono) {
        Uri uri=Uri.parse("tel:"+telefono);
        Intent intent=new Intent(Intent.ACTION_DIAL, uri);
        this.startActivity(intent);
    }

    @Override
    public void realizarLlamada(String telefono) {
        if (confirmarPermisoLlamada()){
            Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telefono));
            this.startActivity(intent);
        }else {
            Toast.makeText(this,"No hay permios",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void mandarMensaje(String contenido) {
        // 1. Declaración del Intent del tipo acción de envío
        Intent sendIntent = new Intent(Intent.ACTION_SEND);

        // 2. Agregado del tipo de contenido en el Intent
        sendIntent.putExtra(Intent.EXTRA_TEXT, contenido);

        // 3. Es necesario definir el tipo del mensaje
        sendIntent.setType("text/plain");

        // 4. Comienzo de la actividad del sistema
        this.startActivity(sendIntent);
    }

    @Override
    public void mandarMensaje(String telefono, String contenido) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + telefono));
        intent.putExtra("sms_body", contenido);
        this.startActivity(intent);
    }


    private boolean confirmarPermisoLlamada() {
        boolean confirmado = false;

        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            this.requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 0);
        }
        else
            confirmado = true;

        return confirmado;
    }

}