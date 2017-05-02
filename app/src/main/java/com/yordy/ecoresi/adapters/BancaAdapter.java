package com.yordy.ecoresi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yordy.ecoresi.R;
import com.yordy.ecoresi.api.modelos.user.Banca;

import java.util.ArrayList;

/**
 * Created by yordy on 05/02/2017.
 */
public class BancaAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private boolean isGrid = false;
    private ArrayList<Banca> lista;

    public BancaAdapter(Context context, ArrayList<Banca> lista) {
        layoutInflater = LayoutInflater.from(context);
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return this.lista.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            if (isGrid) {
                view = layoutInflater.inflate(R.layout.simple_grid_item, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.simple_list_item, parent, false);
            }

            viewHolder = new ViewHolder();
            viewHolder.nombre = (TextView) view.findViewById(R.id.banca_nombre);
            viewHolder.direccion = (TextView) view.findViewById(R.id.banca_direccion);
            viewHolder.telefono = (TextView) view.findViewById(R.id.banca_telefono);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Context context = parent.getContext();
        Banca item = (Banca) this.lista.get(position);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.direccion.setText(item.getDireccion());
        viewHolder.telefono.setText(item.getTelefono());

        return view;
    }

    static class ViewHolder {
        TextView nombre;
        TextView direccion;
        TextView telefono;
    }
}
