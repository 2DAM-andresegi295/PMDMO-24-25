package iesmm.pmdm.pmdm_maps_02;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import iesmm.pmdm.pmdm_maps_02.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        // Add a marker in Sydney and move the camera
        LatLng cortegana = new LatLng(37.9101127, -6.8321521);
        mMap.addMarker(new MarkerOptions().position(cortegana).title("Marker in Cortegana"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cortegana));

        LatLng mediterraneo = new LatLng(37.9101127, 6.8321521);
        mMap.addMarker(new MarkerOptions().position(mediterraneo).title("Marker in Mediterraneo"));

        mMap.addMarker(new MarkerOptions().position(new LatLng(37.2708661,-6.96029))
                .title("Huelva")
                .snippet("Mensaje")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mymarker)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(43.388283,4.1140928)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.388283,4.1140928),8),5000,null);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Toast.makeText(this,marker.getTitle(),Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

    }
}