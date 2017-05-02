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

import com.google.common.collect.ImmutableMap;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.api.MyRestAdapter;
import com.yordy.ecoresi.api.ServerError;
import com.yordy.ecoresi.api.modelos.user.MyUser;
import com.yordy.ecoresi.api.modelos.user.UserRepository;
import com.yordy.ecoresi.remoting.adapters.Adapter;
import com.yordy.ecoresi.views.CustomFontButton;
import com.yordy.ecoresi.views.CustomFontFormEditText;
import com.yordy.ecoresi.views.CustomFontTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yordy on 18/02/2017.
 */
public class DialogValidateEmail extends DialogFragment implements View.OnClickListener {
    @InjectView(R.id.candelar)
    CustomFontButton candelar;
    @InjectView(R.id.guardar)
    CustomFontButton guardar;
    @InjectView(R.id.codigo)
    CustomFontFormEditText codigo;
    private MyRestAdapter restAdapter;
    private UserRepository userRepo;
    private Bundle arg;
    private boolean validate = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restAdapter = MyRestAdapter.getLoopBackAdapter(this.getActivity());
        userRepo = restAdapter.createRepository(UserRepository.class);
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
        final View view = inflater.inflate(R.layout.dialog_validate_email, container, false);
        ButterKnife.inject((Object) this, view);
        this.candelar.setOnClickListener(this);
        this.guardar.setOnClickListener(this);

        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.candelar:
                onDestroyView();
                break;
            case R.id.guardar:
                    if(!this.codigo.getText().toString().equals("")){
                        final Map<String,?> parameters = ImmutableMap.of(
                                "uid", restAdapter.getCurrentUser().getIdJugador(),
                                "token", this.codigo.getText());
                        this.userRepo.invokeStaticMethod(
                                "confirm",
                                parameters,
                                new Adapter.JsonObjectCallback() {
                                    @Override
                                    public void onSuccess(final JSONObject response) {
                                        Log.e("onSuccessssss",response.toString());
                                        validate = true;
                                        onDestroy();
                                    }

                                    @Override
                                    public void onError(final Throwable t) {//at error.error({"error":{"statusCode":460,"name":"Error","message":"Su saldo no es suficiente"}}:0)
                                        StackTraceElement[] trace = t.getStackTrace();
                                        ServerError error = new ServerError(trace);
                                        //Log.e("onError", error.getCode());
                                        if (error.getStatusCode() != null) {
                                            String erro = error.getStatusCode().toString();
                                            switch (erro) {
                                                case "400":
                                                    alertDialogo("Codigo invalido.", null);
                                                    break;
                                                default:
                                                    alertDialogo(" Error: " + error.getName() + "Por favor intente nuevamente, si el problema persiste contacte soporte tecnico.", null);
                                                    break;
                                            }
                                        }
                                    }
                                });
                    }else{
                        this.alertDialogo("Debe ingresar el codigo",null);
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

    /*event the close dialog*/
    public interface OnDismissListener{
        void onDismiss(boolean validate);
    }
    private OnDismissListener onDismissListener;
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onDismissListener != null){
            onDismissListener.onDismiss(validate);
        }
    }
    /*end event the close dialog*/
}
