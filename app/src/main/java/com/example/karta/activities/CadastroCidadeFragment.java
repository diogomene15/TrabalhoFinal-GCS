package com.example.karta.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.karta.R;
import com.example.karta.dao.CidadeDAO;
import com.example.karta.dao.UsuarioDAO;
import com.example.karta.database.AppDatabase;
import com.example.karta.databinding.CadastroCidadeFragmentBinding;
import com.example.karta.databinding.CadastroFragmentBinding;
import com.example.karta.entities.Cidade;
import com.example.karta.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class CadastroCidadeFragment extends Fragment {

    private CadastroCidadeFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = CadastroCidadeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        
        List<String> cidadesAutocomplete = new ArrayList<>();
        if (estado.equals("São Paulo")) {
            cidadesAutocomplete.add("São Paulo");
            cidadesAutocomplete.add("Santos");
            cidadesAutocomplete.add("São José dos Campos");
        } else if (estado.equals("Santa Catarina")) {
            cidadesAutocomplete.add("Florianópolis");
            cidadesAutocomplete.add("Joinville");
            cidadesAutocomplete.add("Blumenau");
        } else if (estado.equals("Sergipe")) {
            cidadesAutocomplete.add("Aracaju");
            cidadesAutocomplete.add("Itabaiana");
            cidadesAutocomplete.add("Estância");
        }


        binding.buttonSubmitCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCidade = binding.inputCidadeNome.getText().toString();
                String nomeEstado = binding.inputEstado.getText().toString();

                if (!nomeCidade.isEmpty() && !nomeEstado.isEmpty()) {
                    new InsertCidadeTask().execute(new Cidade(nomeCidade, nomeEstado));

                } else {
                    Toast.makeText(getActivity(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class InsertCidadeTask extends AsyncTask<Cidade, Void, Cidade> {
        @Override
        protected Cidade doInBackground(Cidade... cidades) {
            AppDatabase db = AppDatabase.getDatabase(getContext());
            CidadeDAO cidadeDAO = db.cidadeDao();
            Cidade cidadeExistente = cidadeDAO.getByCidadeAndEstado(cidades[0].getCidade(), cidades[0].getEstado());
            if (cidadeExistente != null){
                return cidadeExistente;
            }
            cidadeDAO.insert(cidades[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Cidade cidadeExistente) {
            if (cidadeExistente == null){
                Toast.makeText(getActivity(), "Cidade cadastrada com sucesso", Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(CadastroCidadeFragment.this);
                navController.popBackStack();
            } else {
                Toast.makeText(getActivity(), "Cidade já cadastrada", Toast.LENGTH_SHORT).show();
            }
        }
    }

}