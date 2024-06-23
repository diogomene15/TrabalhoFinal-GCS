package com.example.karta.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.karta.R;
import com.example.karta.database.AppDatabase;
import com.example.karta.entities.Cidade;

import java.util.List;
import android.widget.ListView;

public class CidadeAdapter extends ArrayAdapter<Cidade> {
    private Context context;
    private List<Cidade> items;

    public CidadeAdapter(Context context, List<Cidade> items) {
        super(context, R.layout.item_cidade_list, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cidade_list, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.item_text);
        Button button = convertView.findViewById(R.id.buttonExcluirCidade);

        textView.setText(items.get(position).toString());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCidade(items.get(position));
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void deleteCidade(Cidade c){
        AppDatabase db = AppDatabase.getDatabase(getContext());
        db.cidadeDao().delete(c);
    }
}