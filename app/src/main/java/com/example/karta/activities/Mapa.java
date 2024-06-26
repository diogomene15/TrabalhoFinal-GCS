package com.example.karta.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.karta.R;
import com.example.karta.dao.EnderecoDAO;
import com.example.karta.database.AppDatabase;
import com.example.karta.databinding.ActivityMapaBinding;
import com.example.karta.entities.Endereco;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private Endereco endereco;
    private EnderecoDAO enderecoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        enderecoDAO = db.enderecoDao();

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mapa);

        //Configurando mapa na activity
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment!=null){
            mapFragment.getMapAsync(this);
        }

        //Buscando informações de localização de endereco na intent
        Intent intent = getIntent();
        if(intent!= null){
            endereco = (Endereco) intent.getSerializableExtra("endereco");
        }

        // Adicionando nome de endereco ao texto da activity
        if(endereco != null){
            TextView enderecoNome = findViewById(R.id.enderecoNome);
            enderecoNome.setText(endereco.getDescricao());
        }

        // Configurando botão de voltar
        Button sairMapaButton = findViewById(R.id.sairMapaButton);
        sairMapaButton.setOnClickListener(v -> {
            finish();
        });

        Button deletarEnderecoButton = findViewById(R.id.buttonDeletarEndereco);
        deletarEnderecoButton.setOnClickListener(v -> {
            enderecoDAO.delete(endereco);
            Toast.makeText(this, "Endereço deletado", Toast.LENGTH_SHORT).show();
            finish();
        });

        Button editarEnderecoButton = findViewById(R.id.buttonEditEndereco);
        editarEnderecoButton.setOnClickListener(v -> {

            //Navegar para atividade de edição, por Intent, passando endereco como extra
            Intent intentEditEndereco = new Intent(Mapa.this, EditEndereco.class);
            intentEditEndereco.putExtra("endereco", endereco);
            startActivity(intentEditEndereco);
            

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if(endereco!=null){
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(endereco.getLatitude(), endereco.getLongitude()))
                    .title(endereco.getDescricao())
            );
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(
                    new LatLng(
                            endereco.getLatitude(),
                            endereco.getLongitude()
                    )
            ));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        endereco = enderecoDAO.getEndereco(endereco.getEnderecoId());
        if(endereco != null){
            TextView enderecoNome = findViewById(R.id.enderecoNome);
            enderecoNome.setText(endereco.getDescricao());
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment!=null){
            mapFragment.getMapAsync(this);
        }
    }
    private void showNavigationOptions() {
        final CharSequence[] options = {"Google Maps", "Waze"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Escolha um aplicativo de navegação");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openGoogleMaps();
                        break;
                    case 1:
                        openWaze();
                        break;
                }
            }
        });
        builder.show();
    }

    private void openGoogleMaps() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=My+Location");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Se o Google Maps não estiver instalado
            Toast.makeText(getContext(), "Google Maps não está instalado", Toast.LENGTH_SHORT).show();
        }
    }

    private void openWaze() {
        try {
            String url = "waze://?q=My+Location";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception ex) {
            // Se o Waze não estiver instalado
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
            startActivity(intent);
        }
    }
}