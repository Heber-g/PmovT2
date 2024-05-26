package com.example.controleenderecos.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controleenderecos.database.LocalDatabase;
import com.example.controleenderecos.databinding.ActivityMapaBinding;
import com.example.controleenderecos.entity.Endereco;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityMapaBinding binding;
    private GoogleMap mapa;
    private LocalDatabase db;
    private MapView mapview;
    private int endID;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = LocalDatabase.getDatabase(getApplicationContext());

        binding = ActivityMapaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        endID = getIntent().getIntExtra("Endereco_Selecionado_ID", -1);

        mapview = binding.mapView;
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapview.onCreate(mapViewBundle);
        mapview.getMapAsync(this);

    binding.btnVoltarMapa.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        MapsInitializer.initialize(this);
        Endereco novoEnd = db.enderecos().getEndID(endID);

        if (novoEnd != null) {
        LatLng defaultLocation = new LatLng(novoEnd.getLatitude(), novoEnd.getLongitude());
        mapa.addMarker(new MarkerOptions().position(defaultLocation).title("Marcador em " + novoEnd.getDescricao()));
        mapa.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation,
                15));
        }else{
            Toast.makeText(this, "Endereço não encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    protected void onStart() {
        super.onStart();
        mapview.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
    }

    @Override
    protected void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapview.onSaveInstanceState(mapViewBundle);
    }
}