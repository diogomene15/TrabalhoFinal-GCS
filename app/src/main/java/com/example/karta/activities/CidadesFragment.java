package com.example.karta.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.karta.R;
import com.example.karta.dao.CidadeDAO;
import com.example.karta.dao.UsuarioDAO;
import com.example.karta.database.AppDatabase;
import com.example.karta.databinding.CidadesFragmentBinding;
import com.example.karta.databinding.LoginFragmentBinding;
import com.example.karta.entities.Cidade;
import com.example.karta.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class CidadesFragment extends Fragment {

    private CidadesFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = CidadesFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CidadeAdapter cidades = new CidadeAdapter(view.getContext(), this.getAllCidades());

        binding.listCidades.setAdapter(cidades);
        //binding.listCidades.notify();
        //runOnUiThread(new Runnable() {
        //    public void run() {
        //        adapter.notifyDataSetChanged();
        //    }
        //});

        binding.buttonAddCidade.setOnClickListener(v ->
                NavHostFragment.findNavController(CidadesFragment.this)
                        .navigate(R.id.action_CidadesFragment_to_CadastroCidade)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<Cidade> getAllCidades(){
        AppDatabase db = AppDatabase.getDatabase(getContext());
        List<Cidade> cidades = db.cidadeDao().getAll();
        return cidades;
    }
}