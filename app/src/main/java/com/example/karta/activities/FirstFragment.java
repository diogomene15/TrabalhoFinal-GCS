package com.example.karta.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.karta.R;
import com.example.karta.databinding.FragmentFirstBinding;
import com.example.karta.entities.Endereco;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(v ->{
                    Endereco end = new Endereco();
                    end.setDescricao("Tupi");
                    end.setLatitude(-20.5000477);
                    end.setLongitude(-54.6191862);
                    Intent intent = new Intent(getActivity(), Mapa.class);
                    intent.putExtra("endereco", end);
                    startActivity(intent);
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}