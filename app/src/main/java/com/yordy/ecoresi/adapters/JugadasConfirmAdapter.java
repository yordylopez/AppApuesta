package com.yordy.ecoresi.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yordy.ecoresi.R;
import com.yordy.ecoresi.api.MyRestAdapter;
import com.yordy.ecoresi.api.modelos.user.AnimalitoJugado;
import com.yordy.ecoresi.api.modelos.user.MyUser;
import com.yordy.ecoresi.dialogs.DialogConfimAnimalito;
import com.yordy.ecoresi.fragments.ReportsFragment;
import com.yordy.ecoresi.interfaces.jugadasConfirmAdapterOnClickListenerHack;
import com.yordy.ecoresi.views.CustomFontTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class JugadasConfirmAdapter extends Adapter<JugadasConfirmAdapter.ViewHolder> {
    protected static final String TAG;
    private Activity activity;
    private LayoutInflater inflater;
    private jugadasConfirmAdapterOnClickListenerHack onClickListenerHack;
    private MyUser currentUser;
    private MyRestAdapter restAdapter;
    private List<Map> lista = new ArrayList<Map>();
    private FragmentManager fragmentManag;

    public void setOnClickListenerHack(jugadasConfirmAdapterOnClickListenerHack onClickListenerHack) {
        this.onClickListenerHack = onClickListenerHack;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManag = fragmentManager;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.txt_hora_jugada)
        CustomFontTextView txt_hora_jugada;
        @InjectView(R.id.txt_monto)
        CustomFontTextView txt_monto;
        @InjectView(R.id.btn_editar)
        CustomFontTextView btn_editar;
        @InjectView(R.id.btn_eliminar)
        CustomFontTextView btn_eliminar;
        @InjectView(R.id.foto_animalito)
        ImageView foto_animalito;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject((Object) this, itemView);
            btn_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(getAdapterPosition());
                }
            });
            btn_eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogo("¿Esta seguro que desea eliminar esta jugada?.",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    },new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            currentUser.jugadasRemove(getAdapterPosition());
                            lista =  currentUser.getEnviarJugadas();
                            notifyDataSetChanged();
                            if(onClickListenerHack != null){
                                onClickListenerHack.RemoveOnClickListener(getAdapterPosition());
                            }
                            dialogInterface.cancel();
                        }
                    });
                }
            });
        }

        private void showDialog(int position){
            Bundle args = new Bundle();
            args.putString(DialogConfimAnimalito.keyOperacion,DialogConfimAnimalito.operacionEditar);
            args.putInt(DialogConfimAnimalito.keyPosition,position);
            FragmentManager fragmentManager =  fragmentManag;
            DialogConfimAnimalito dialog = new DialogConfimAnimalito();
            dialog.setArguments(args);
            dialog.setOnDismissListener(new DialogConfimAnimalito.OnDismissListener(){

                @Override
                public void onDismiss(DialogConfimAnimalito dialogConfimAnimalito) {
                    lista =  currentUser.getEnviarJugadas();
                    notifyDataSetChanged();
                    if(onClickListenerHack != null){
                        onClickListenerHack.UpdateOnClickListener(getAdapterPosition());
                    }
                }
            });
            dialog.show(fragmentManager, "Editar");
        }

        private void alertDialogo(String msg, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener listener2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(msg);
            builder.setCancelable(true);
            if (listener == null) {
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
            } else {
                builder.setNegativeButton("Cancelar", listener);
                builder.setPositiveButton("Aceptar", listener2);
            }
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    static {
        TAG = JugadasConfirmAdapter.class.getSimpleName();
    }

    public JugadasConfirmAdapter(Activity activity) {
        this.inflater = LayoutInflater.from(activity);
        restAdapter = MyRestAdapter.getLoopBackAdapter(activity);
        currentUser = restAdapter.getCurrentUser();
        this.lista =  currentUser.getEnviarJugadas();
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.item_animalito_fragment_jugadas, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Map jugada = this.lista.get(i);
        viewHolder.txt_hora_jugada.setText((String) jugada.get(DialogConfimAnimalito.keyHora));
        viewHolder.txt_monto.setText((String) jugada.get(DialogConfimAnimalito.keyMonto));
        viewHolder.itemView.setTag(jugada);
        String animal = (String) jugada.get(DialogConfimAnimalito.keyAnimalito);
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
    public void setList(List<Map> lista){
        clear();
        this.lista = lista;
        this.notifyDataSetChanged();
    }
    public void clearList(){
        this.lista =  new ArrayList();
        this.notifyDataSetChanged();
    }
}
