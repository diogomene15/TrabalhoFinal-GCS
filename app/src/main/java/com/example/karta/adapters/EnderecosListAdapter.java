package com.example.karta.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.karta.R;
import com.example.karta.activities.Mapa;
import com.example.karta.entities.Endereco;
import com.example.karta.entities.EnderecoCidade;

import java.util.List;

public class EnderecosListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<EnderecoCidade> items;

    public EnderecosListAdapter(Context context, List<EnderecoCidade> items){
        this.items = items;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class AdapterItemAux{
        TextView enderecoDesc;
        ImageButton btnMapa;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterItemAux itemAux;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_endereco_list, null);
            itemAux = new AdapterItemAux();
            itemAux.enderecoDesc = (TextView) convertView.findViewById(R.id.enderecoDescText);
            itemAux.btnMapa = (ImageButton) convertView.findViewById(R.id.mapButton);
        }else{
            itemAux = (AdapterItemAux) convertView.getTag();
        }

        EnderecoCidade item = (EnderecoCidade) getItem(position);

        itemAux.enderecoDesc.setText(String.format("%s (%s - %s)", item.endereco.getDescricao(), item.cidade.getCidade(), item.cidade.getEstado()));
        itemAux.btnMapa.setOnClickListener(v -> {
            //iniciar atividade de Mapa com intent com extra de endereco
            Intent intent = new Intent(v.getContext(), Mapa.class);
            intent.putExtra("endereco", item.endereco);
            v.getContext().startActivity(intent);
        });
        return convertView;
    }
}
