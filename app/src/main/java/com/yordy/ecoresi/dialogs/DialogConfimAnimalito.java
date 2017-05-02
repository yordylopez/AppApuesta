package com.yordy.ecoresi.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.yordy.ecoresi.R;
import com.yordy.ecoresi.api.MyRestAdapter;
import com.yordy.ecoresi.api.modelos.user.MyUser;
import com.yordy.ecoresi.views.CustomFontButton;
import com.yordy.ecoresi.views.CustomFontFormEditText;
import com.yordy.ecoresi.views.CustomFontTextView;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yordy on 18/02/2017.
 */
public class DialogConfimAnimalito extends DialogFragment implements View.OnClickListener {
    public static String operacionGuardar = "guardar";
    public static String operacionEditar = "editar";
    public static String keyOperacion = "operacion";
    public static String keyAnimalito = "animalito";
    public static String keyMonto = "monto";
    public static String keyHora = "hora";
    public static String keyPosition = "position";
    @InjectView(R.id.txt_animalito)
    CustomFontTextView txt_animalito;
    @InjectView(R.id.horas)
    Spinner horas;
    @InjectView(R.id.candelar)
    CustomFontButton candelar;
    @InjectView(R.id.guardar)
    CustomFontButton guardar;
    @InjectView(R.id.monto)
    CustomFontFormEditText monto;
    private MyRestAdapter restAdapter;
    private MyUser currentUser;
    private Bundle arg;
    private String animalito_select;
    private String array_horas[];
    private String operacion;
    private Map jugada;
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restAdapter = MyRestAdapter.getLoopBackAdapter(this.getActivity());
        currentUser = restAdapter.getCurrentUser();
        arg = getArguments();
        operacion = arg.getString(DialogConfimAnimalito.keyOperacion);
        if(operacion.equals(DialogConfimAnimalito.operacionGuardar)){
            animalito_select = arg.getString(DialogConfimAnimalito.keyAnimalito);
        }else if(operacion.equals(DialogConfimAnimalito.operacionEditar)){
            position = arg.getInt(DialogConfimAnimalito.keyPosition);
            jugada = currentUser.getEnviarJugadas().get(position);
            animalito_select = (String) jugada.get(DialogConfimAnimalito.keyAnimalito);
        }

        array_horas=new String[11];
        array_horas[0]="Por favor seleccione la hora";
        array_horas[1]="10 am";
        array_horas[2]="11 am";
        array_horas[3]="12 pm";
        array_horas[4]="1 pm";
        array_horas[5]="2 pm";
        array_horas[6]="3 pm";
        array_horas[7]="4 pm";
        array_horas[8]="5 pm";
        array_horas[9]="6 pm";
        array_horas[10]="7 pm";

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_confirm_animalito, container, false);
        ButterKnife.inject((Object) this, view);
        txt_animalito.setText("Jugando: "+animalito_select);
        ArrayAdapter adapter = new ArrayAdapter(this.getActivity(),R.layout.spinner_item, array_horas);
        horas.setAdapter(adapter);
        candelar.setOnClickListener(this);
        guardar.setOnClickListener(this);
        if(operacion.equals(DialogConfimAnimalito.operacionEditar)){
            horas.setSelection(getHourPosition((String) jugada.get(DialogConfimAnimalito.keyHora)));
            monto.setText((String) jugada.get(DialogConfimAnimalito.keyMonto));
        }

        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.candelar:
                onDestroyView();
                break;
            case R.id.guardar:
                if(monto.getText().toString().equals("")){
                    alertDialogo("Por favor indique el monto",null);
                }else{
                    if(horas.getSelectedItemPosition() == 0){
                        alertDialogo("Por favor indique la hora",null);
                    }else{
                        int hora = new Time(System.currentTimeMillis()).getHours();
                        Log.e("hora actual", Integer.toString(hora));
                        if(hora < getHourFormat(horas.getSelectedItem().toString())){
                            Map<String, Object> animalito = new HashMap<String, Object>();
                            animalito.put(DialogConfimAnimalito.keyAnimalito,animalito_select);
                            animalito.put(DialogConfimAnimalito.keyHora,horas.getSelectedItem().toString());
                            animalito.put(DialogConfimAnimalito.keyMonto,monto.getText().toString());
                            if(operacion.equals(DialogConfimAnimalito.operacionEditar)){
                                currentUser.getEnviarJugadas().set(position,animalito);
                            }else{
                                if(!currentUser.existJugada(animalito)){
                                    currentUser.jugadasAdd(animalito);
                                }else{
                                    alertDialogo("Ya realizo esta jugada, puede eliminarla o modificar el monto presionando la opcion SIGUIENTE en la barra superior.",null);
                                }
                            }
                            onDestroyView();
                        }else{
                            alertDialogo("Debe jugar una hora mayor a la actual",null);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private void alertDialogo(String msg, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage(msg);
        builder.setCancelable(true);
        if (listener == null) {
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
        } else {
            builder.setNegativeButton("Ok", listener);
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    private int getHourFormat(String hora){
        switch (hora) {
            case "10 am": return 10;
            case "11 am": return 11;
            case "12 pm": return 12;
            case "1 pm": return 13;
            case "2 pm": return 14;
            case "3 pm": return 15;
            case "4 pm": return 16;
            case "5 pm": return 17;
            case "6 pm": return 18;
            case "7 pm": return 19;
            default:  return -1;
        }
    }
    private int getHourPosition(String hora){
        switch (hora) {
            case "10 am": return 1;
            case "11 am": return 2;
            case "12 pm": return 3;
            case "1 pm": return 4;
            case "2 pm": return 5;
            case "3 pm": return 6;
            case "4 pm": return 7;
            case "5 pm": return 8;
            case "6 pm": return 9;
            case "7 pm": return 10;
            default:  return -1;
        }
    }


    /*event the close dialog*/
    public interface OnDismissListener{
        void onDismiss(DialogConfimAnimalito dialogConfimAnimalito);
    }
    private OnDismissListener onDismissListener;
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onDismissListener != null){
            onDismissListener.onDismiss(this);
        }
    }
    /*end event the close dialog*/
}
