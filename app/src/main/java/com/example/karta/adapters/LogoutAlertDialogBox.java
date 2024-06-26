package com.example.karta.adapters;

import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LogoutAlertDialogBox {
    public static void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getContext())
            .setTitle("Confirmar saída")
            .setMessage("Tem certeza de que deseja sair?")
            .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Navega para a tela de login
                    NavHostFragment.findNavController(EnderecosFragment.this)
                            .navigate(R.id.action_EnderecosFragment_to_LoginFragment);
                }
            })
            .setNegativeButton("Cancelar", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }
}
