package com.example.karta.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.karta.R;
import com.example.karta.dao.UsuarioDAO;
import com.example.karta.database.AppDatabase;
import com.example.karta.entities.Usuario;
import com.example.karta.useCases.CurrentUser;

public class EditUsuarioActivity extends AppCompatActivity {

    private Usuario usuario;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_usuario);

        usuario = CurrentUser.getInstance().getUser();

        if(usuario!= null){
            EditText nomeInput = (EditText) findViewById(R.id.nomeTextInput);
            EditText emailInput = (EditText) findViewById(R.id.emailTextInput);
            EditText novaSenha = (EditText) findViewById(R.id.passwordTextInput);
            EditText oldSenha = (EditText) findViewById(R.id.oldPasswordTextInput);

            nomeInput.setText(usuario.getNome());
            emailInput.setText(usuario.getEmail());

            Button btnSalvar = (Button) findViewById(R.id.buttonSalvarEdit);

            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            usuarioDAO = db.usuarioDao();
            btnSalvar.setOnClickListener(v -> {
                Usuario oldReg = usuarioDAO.getUser(usuario.getUsuarioId());
                if(oldReg == null){
                    return;
                }
                if(!oldSenha.getText().toString().equals(oldReg.getSenha())){
                    Toast.makeText(this, "Senha incorreta", Toast.LENGTH_SHORT).show();
                    return;
                }
                usuario.setNome(nomeInput.getText().toString());
                usuario.setEmail(emailInput.getText().toString());
                if(novaSenha!=null && novaSenha.length() > 0){
                    usuario.setSenha(novaSenha.getText().toString());
                }

                usuarioDAO.update(usuario);
                finish();
            });

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}