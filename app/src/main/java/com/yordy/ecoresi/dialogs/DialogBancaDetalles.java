package com.yordy.ecoresi.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
public class DialogBancaDetalles extends DialogFragment implements View.OnClickListener{
    @InjectView(R.id.banca_nombre)
    CustomFontTextView banca_nombre;
    @InjectView(R.id.banca_direccion)
    CustomFontTextView banca_direccion;
    @InjectView(R.id.ok)
    CustomFontButton ok;
    private Bundle arg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arg = getArguments();

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
        final View view = inflater.inflate(R.layout.dialog_banca_detalles, container, false);
        ButterKnife.inject((Object) this, view);
        banca_nombre.setText(arg.getString("nombre"));
        banca_direccion.setText(arg.getString("direccion"));
        ok.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                onDestroyView();
                break;
            default:
                break;
        }
    }


    /*event the close dialog*/
    public interface OnDismissListener{
        void onDismiss(DialogBancaDetalles dialogConfimAnimalito);
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
