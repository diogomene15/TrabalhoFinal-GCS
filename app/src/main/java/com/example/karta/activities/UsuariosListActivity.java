package com.example.karta.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.karta.R;
import com.example.karta.dao.UsuarioDAO;
import com.example.karta.database.AppDatabase;
import com.example.karta.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuariosListActivity extends AppCompatActivity {
    private UsuarioDAO usuarioDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuarios_list);

        ListView listViewUsuarios = (ListView) findViewById(R.id.listaUsuarios);

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        usuarioDAO = db.usuarioDao();

        List<Usuario> listaUsuarios = usuarioDAO.getAll();

        //create a list of strings of the user's names
        List<String> listaUsuariosNomes = new ArrayList<>();
        for (Usuario u : listaUsuarios){
            listaUsuariosNomes.add(u.getNome());
        }

        listViewUsuarios.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,listaUsuariosNomes));

        Button voltar = (Button) findViewById(R.id.buttonVoltarFromUsuarios);
        voltar.setOnClickListener(v -> {
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}