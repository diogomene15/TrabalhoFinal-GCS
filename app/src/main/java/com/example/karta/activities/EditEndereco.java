package com.example.karta.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.karta.R;
import com.example.karta.database.AppDatabase;
import com.example.karta.entities.Cidade;
import com.example.karta.entities.Endereco;

import java.util.List;

public class EditEndereco extends AppCompatActivity {
    private Endereco endereco;

    private List<Cidade> getAllCidades(){
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        return db.cidadeDao().getAll();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_endereco);

        endereco = (Endereco) getIntent().getSerializableExtra("endereco");
        if(endereco == null){
            finish();
        }

        EditText latitudeEdit = findViewById(R.id.inputLatitudeEd);
        EditText longitudeEdit = findViewById(R.id.inputLongitudeEd);
        EditText descricaoEdit = findViewById(R.id.inputDescricaoEd);

        List<Cidade> cidadesList = getAllCidades();
        Spinner cidadeSpinner = findViewById(R.id.spinnerCidadesEd);
        ArrayAdapter<Cidade> adapt = new ArrayAdapter<Cidade>(this, android.R.layout.simple_spinner_item, cidadesList);
        adapt.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        cidadeSpinner.setAdapter(adapt);

        //Procurar qual a posicao da atual cidade (em endereco), e setala no spinner
        for(int i = 0; i < cidadesList.size(); i++){
            if(cidadesList.get(i).getCidadeId() == endereco.getCidadeIdFk()){
                cidadeSpinner.setSelection(i);
                break;
            }
        }

        latitudeEdit.setText(String.valueOf(endereco.getLatitude()));
        longitudeEdit.setText(String.valueOf(endereco.getLongitude()));
        descricaoEdit.setText(endereco.getDescricao());

        Button editButton = findViewById(R.id.buttonEditarEnd);

        editButton.setOnClickListener(v -> {
            endereco.setLatitude(Double.parseDouble(latitudeEdit.getText().toString()));
            endereco.setLongitude(Double.parseDouble(longitudeEdit.getText().toString()));
            endereco.setDescricao(descricaoEdit.getText().toString());

            Cidade cidade = (Cidade) cidadeSpinner.getSelectedItem();
            endereco.setCidadeIdFk(cidade.getCidadeId());

            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            db.enderecoDao().update(endereco);
            Toast.makeText(this, "Endereço editado", Toast.LENGTH_SHORT).show();
            finish();

        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}