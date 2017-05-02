package com.yordy.ecoresi.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.classes.Report;
import com.yordy.ecoresi.views.CustomFontTextView;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    protected static final String TAG;
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Report> lista;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @InjectView(R.id.descripcion)
        CustomFontTextView descripcion;
        @InjectView(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject((Object) this, itemView);
        }
    }

    static {
        TAG = ReportAdapter.class.getSimpleName();
    }

    public ReportAdapter(Activity activity, ArrayList<Report> lista) {
        this.inflater = LayoutInflater.from(activity);
        this.lista = lista;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.item_report, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Report item = (Report) this.lista.get(i);
        viewHolder.descripcion.setText(item.getDescripcion());
        Picasso.with(this.activity).load(item.getImage()).placeholder(this.activity.getResources().getDrawable(R.drawable.logo_login)).error(this.activity.getResources().getDrawable(R.drawable.logo_login)).into(viewHolder.image);
        viewHolder.itemView.setTag(item);
    }

    public int getItemCount() {
        return this.lista.size();
    }
}
