package iesmm.pmdm.pmdm_t4_users;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import iesmm.pmdm.pmdm_t4_users.databinding.*;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener  {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private File usuarios;
    private String usuarioLogueado;
    @Override
    protected void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);

        binding= ActivityMapsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        //Sacamos el usuario logueado del Intent
        usuarioLogueado = getIntent().getStringExtra("USUARIO_LOGUEADO");
        Toast.makeText(this, "Usuario logueado: " + usuarioLogueado, Toast.LENGTH_SHORT).show();

         usuarios= this.getFileStreamPath("users.csv");

        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        Geocoder geocoder = new Geocoder(this);
        LatLng posicionInicial = new LatLng(0,0);
        boolean ubicacionEncontrada=false;
        String[] datosUsuario = new String[0];
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(usuarios));
            List<Address> direcciones =new ArrayList<>();
            String linea;
            String[] datos;
            LatLng position;


            while ((linea = bufferedReader.readLine()) != null){
                datos=linea.split(";");
                direcciones=geocoder.getFromLocationName(datos[4],1);

                assert direcciones != null;
                if (!direcciones.isEmpty()){

                    if (datos[2].equals(usuarioLogueado)){
                        datosUsuario=datos;
                        Address direccion=direcciones.get(0);
                        position=new LatLng(direccion.getLatitude(),direccion.getLongitude());
                        posicionInicial=position;
                        ubicacionEncontrada=true;
                    }else {
                        Address direccion=direcciones.get(0);
                        position=new LatLng(direccion.getLatitude(),direccion.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(position).title(datos[0]));
                    }
                }


            }
            if (!ubicacionEncontrada){
                Toast.makeText(MapActivity.this,"No se encontró la ubicación",Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (ubicacionEncontrada){
            String snippetUsuario=datosUsuario[2]+"\n"+datosUsuario[3]+"\n"+datosUsuario[4]+"\n";
            mMap.addMarker(new MarkerOptions().position(posicionInicial).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title(datosUsuario[0]).snippet(snippetUsuario));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicionInicial,12),2000,null);
        }

    }
}
