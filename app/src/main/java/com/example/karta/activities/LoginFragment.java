package com.example.karta.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.karta.R;
import com.example.karta.dao.UsuarioDAO;
import com.example.karta.database.AppDatabase;
import com.example.karta.databinding.LoginFragmentBinding;
import com.example.karta.entities.Usuario;
import com.example.karta.useCases.CurrentUser;

public class LoginFragment extends Fragment {

    private LoginFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CurrentUser cu = CurrentUser.getInstance();
        cu.setUser(null);
        binding.buttonSubmitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAd = binding.inputLoginEmailAddress.getText().toString();
                String pwd = binding.inputLoginPassword.getText().toString();

                if (!emailAd.isEmpty() && !pwd.isEmpty()) {
                    new GetUserTask().execute(new Usuario(emailAd, pwd));
                } else {
                    Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.buttonCadastroFragmentNav.setOnClickListener(v ->
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_CadastroFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class GetUserTask extends AsyncTask<Usuario, Void, Usuario> {
        @Override
        protected Usuario doInBackground(Usuario... usuarios) {
            Usuario usuarioInput = usuarios[0];
            AppDatabase db = AppDatabase.getDatabase(getContext());
            UsuarioDAO usuarioDAO = db.usuarioDao();
            Usuario usuarioExistente = usuarioDAO.getUserByEmail(usuarioInput.getEmail());
            if (usuarioExistente != null){
                if (usuarioExistente.getSenha().equals(usuarioInput.getSenha())) {
                    return usuarioExistente;
                } else if (!usuarioExistente.getSenha().equals(usuarioInput.getSenha())){
                    return new Usuario();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            if (usuario == null) {
                Toast.makeText(getActivity(), "Email não cadastrado", Toast.LENGTH_SHORT).show();
            } else if (usuario.getSenha() == null){
                Toast.makeText(getActivity(), "Senha incorreta", Toast.LENGTH_SHORT).show();
            } else {
                CurrentUser cu = CurrentUser.getInstance();
                cu.setUser(usuario);
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_EnderecosFragment);
            }
        }
    }

}