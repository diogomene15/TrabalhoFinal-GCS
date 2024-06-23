package com.example.karta.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karta.R;
import com.example.karta.adapters.EnderecosListAdapter;
import com.example.karta.dao.EnderecoDAO;
import com.example.karta.database.AppDatabase;
import com.example.karta.databinding.FragmentEnderecosBinding;
import com.example.karta.entities.Endereco;
import com.example.karta.entities.EnderecoCidade;

import java.util.List;

public class EnderecosFragment extends Fragment {
    EnderecoDAO enderecoDAO;
    FragmentEnderecosBinding binding;
    public EnderecosFragment() {
        // Required empty public constructor
    }

    public static EnderecosFragment newInstance() {
        return new EnderecosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEnderecosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonAddCidade.setOnClickListener(v -> {
            NavHostFragment.findNavController(EnderecosFragment.this)
                    .navigate(R.id.action_EnderecosFragment_to_CadastroEnderecoFragment);
        });


         AppDatabase db = AppDatabase.getDatabase(getContext());
         enderecoDAO = db.enderecoDao();

         List<EnderecoCidade> enderecosCidade = enderecoDAO.getAllEnderecoCidade();

         binding.listCidades.setAdapter(new EnderecosListAdapter(getContext(), enderecosCidade));



        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        AppDatabase db = AppDatabase.getDatabase(getContext());
        enderecoDAO = db.enderecoDao();

        List<EnderecoCidade> enderecosCidade = enderecoDAO.getAllEnderecoCidade();

        binding.listCidades.setAdapter(new EnderecosListAdapter(getContext(), enderecosCidade));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}