package com.yordy.ecoresi.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.malmstein.fenster.view.FensterVideoView;
import com.squareup.picasso.Picasso;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.api.modelos.user.AnimalitoJugado;
import com.yordy.ecoresi.classes.Post;
import com.yordy.ecoresi.dialogs.DialogConfimAnimalito;
import com.yordy.ecoresi.views.CustomFontTextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class JugadasAdapter extends Adapter<JugadasAdapter.ViewHolder> {
    protected static final String TAG;
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<AnimalitoJugado> lista;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.txt_hora_jugada)
        CustomFontTextView txt_hora_jugada;
        @InjectView(R.id.txt_monto)
        CustomFontTextView txt_monto;
        @InjectView(R.id.txt_status)
        CustomFontTextView txt_status;
        @InjectView(R.id.btn_mas)
        LinearLayout btn_mas;
        @InjectView(R.id.foto_animalito)
        ImageView foto_animalito;
        @InjectView(R.id.itenContent)
        LinearLayout itenContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject((Object) this, itemView);
        }
    }

    static {
        TAG = JugadasAdapter.class.getSimpleName();
    }

    public JugadasAdapter(Activity activity, ArrayList<AnimalitoJugado> lista) {
        this.inflater = LayoutInflater.from(activity);
        this.lista = lista;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.item_animalito_perfil, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AnimalitoJugado item = (AnimalitoJugado) this.lista.get(i);
        viewHolder.txt_hora_jugada.setText(item.getHora());
        viewHolder.txt_monto.setText(Float.toString(item.getMonto()));
        viewHolder.txt_status.setText(item.getStatus());
        viewHolder.itemView.setTag(item);
        if(i == this.getItemCount()-1){
            viewHolder.btn_mas.setVisibility(View.VISIBLE);
        }else{
            viewHolder.btn_mas.setVisibility(View.INVISIBLE);
        }
        String status = (String) item.getStatus();
        switch (status) {
            case "Pendiente":
                viewHolder.itenContent.setBackgroundColor(Color.parseColor("#d8dc65"));
                break;
            case "Jugado":
                viewHolder.itenContent.setBackgroundColor(Color.parseColor("#4472de"));
                break;
            case "Cancelado":
                viewHolder.itenContent.setBackgroundColor(Color.parseColor("#e94545"));
                break;
            case "Ganado":
                viewHolder.itenContent.setBackgroundColor(Color.parseColor("#0bc769"));
                break;
            default:
                break;
        }
        String animal = (String) item.getAnimalito();
        switch (animal) {
            case "Delfin":
                viewHolder.foto_animalito.setImageResource(R.drawable.delfin);
                break;
            case "Ballena":
                viewHolder.foto_animalito.setImageResource(R.drawable.ballena);
                break;
            case "Carnero":
                viewHolder.foto_animalito.setImageResource(R.drawable.carnero);
                break;
            case "Toro":
                viewHolder.foto_animalito.setImageResource(R.drawable.toro);
                break;
            case "Ciempies":
                viewHolder.foto_animalito.setImageResource(R.drawable.ciempies);
                break;
            case "Alacran":
                viewHolder.foto_animalito.setImageResource(R.drawable.alacran);
                break;
            case "Leon":
                viewHolder.foto_animalito.setImageResource(R.drawable.leon);
                break;
            case "Rana":
                viewHolder.foto_animalito.setImageResource(R.drawable.rana);
                break;
            case "Perico":
                viewHolder.foto_animalito.setImageResource(R.drawable.perico);
                break;
            case "Raton":
                viewHolder.foto_animalito.setImageResource(R.drawable.raton);
                break;
            case "Aguila":
                viewHolder.foto_animalito.setImageResource(R.drawable.aguila);
                break;
            case "Tigre":
                viewHolder.foto_animalito.setImageResource(R.drawable.tigre);
                break;
            case "Gato":
                viewHolder.foto_animalito.setImageResource(R.drawable.gato);
                break;
            case "Caballo":
                viewHolder.foto_animalito.setImageResource(R.drawable.caballo);
                break;
            case "Mono":
                viewHolder.foto_animalito.setImageResource(R.drawable.mono);
                break;
            case "Paloma":
                viewHolder.foto_animalito.setImageResource(R.drawable.paloma);
                break;
            case "Zorro":
                viewHolder.foto_animalito.setImageResource(R.drawable.zorro);
                break;
            case "Oso":
                viewHolder.foto_animalito.setImageResource(R.drawable.oso);
                break;
            case "Pavo":
                viewHolder.foto_animalito.setImageResource(R.drawable.pavo);
                break;
            case "Burro":
                viewHolder.foto_animalito.setImageResource(R.drawable.burro);
                break;
            case "Chivo":
                viewHolder.foto_animalito.setImageResource(R.drawable.chivo);
                break;
            case "Cochino":
                viewHolder.foto_animalito.setImageResource(R.drawable.cochino);
                break;
            case "Gallo":
                viewHolder.foto_animalito.setImageResource(R.drawable.gallo);
                break;
            case "Camello":
                viewHolder.foto_animalito.setImageResource(R.drawable.camello);
                break;
            case "Zebra":
                viewHolder.foto_animalito.setImageResource(R.drawable.zebra);
                break;
            case "Iguana":
                viewHolder.foto_animalito.setImageResource(R.drawable.iguana);
                break;
            case "Gallina":
                viewHolder.foto_animalito.setImageResource(R.drawable.gallina);
                break;
            case "Vaca":
                viewHolder.foto_animalito.setImageResource(R.drawable.vaca);
                break;
            case "Perro":
                viewHolder.foto_animalito.setImageResource(R.drawable.perro);
                break;
            case "Zamuro":
                viewHolder.foto_animalito.setImageResource(R.drawable.zamuro);
                break;
            case "Elefante":
                viewHolder.foto_animalito.setImageResource(R.drawable.elefante);
                break;
            case "Caiman":
                viewHolder.foto_animalito.setImageResource(R.drawable.caiman);
                break;
            case "Lapa":
                viewHolder.foto_animalito.setImageResource(R.drawable.lapa);
                break;
            case "Ardilla":
                viewHolder.foto_animalito.setImageResource(R.drawable.ardilla);
                break;
            case "Pescado":
                viewHolder.foto_animalito.setImageResource(R.drawable.pescado);
                break;
            case "falta":
                viewHolder.foto_animalito.setImageResource(R.drawable.ballena);
                break;
            case "falta1":
                viewHolder.foto_animalito.setImageResource(R.drawable.ballena);
                break;
            case "falta2":
                viewHolder.foto_animalito.setImageResource(R.drawable.ballena);
                break;
            default:
                break;
        }
    }

    public int getItemCount() {
        return this.lista.size();
    }
    public void clear(){
        int size = getItemCount();
        if(size>0){
            for (int i=0;i<size;i++){
                this.lista.remove(0);
            }
            this.lista.clear();
            this.notifyItemRangeRemoved(0,size);
            this.notifyDataSetChanged();
        }
    }
    public void setList(ArrayList<AnimalitoJugado> lista){
        clear();
        this.lista = lista;
        this.notifyDataSetChanged();
    }
    public void clearList(){
        this.lista =  new ArrayList();
        this.notifyDataSetChanged();
    }
}
