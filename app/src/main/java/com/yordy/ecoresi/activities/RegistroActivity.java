package com.yordy.ecoresi.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andreabaccega.widget.FormEditText;
import com.google.common.collect.ImmutableMap;
import com.yordy.ecoresi.api.ServerError;
import com.yordy.ecoresi.api.modelos.user.MyUser;
import com.yordy.ecoresi.api.modelos.user.UserRepository;
import com.yordy.ecoresi.customValidation.PasswordValidation;
import com.yordy.ecoresi.loopback.callbacks.VoidCallback;
import com.yordy.ecoresi.remoting.JsonUtil;
import com.yordy.ecoresi.remoting.Repository;
import com.yordy.ecoresi.remoting.adapters.Adapter;
import com.yordy.ecoresi.BuildConfig;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.api.MyRestAdapter;
import com.yordy.ecoresi.utils.DialogUtils;
import com.yordy.ecoresi.views.CustomFontFormEditText;
import com.yordy.ecoresi.views.CustomFontTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class RegistroActivity extends ActionBarActivity implements OnClickListener {
    @InjectView(R.id.contrasena)
    CustomFontFormEditText contrasena;
    @InjectView(R.id.rpcontrasena)
    CustomFontFormEditText rpcontrasena;
    @InjectView(R.id.fecha_nacimiento)
    CustomFontFormEditText fecha_nacimiento;
    @InjectView(R.id.email)
    CustomFontFormEditText email;
    @InjectView(R.id.iniciar_sesion)
    CustomFontTextView login;
    @InjectView(R.id.nombre)
    CustomFontFormEditText nombre;
    @InjectView(R.id.apellido)
    CustomFontFormEditText apellido;
    @InjectView(R.id.cedula)
    CustomFontFormEditText cedula;
    @InjectView(R.id.registrarme)
    Button registrarme;
    String fecha_nac = null;
    private int mYear, mMonth, mDay;
    protected ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_registro);
        ButterKnife.inject((Activity) this);
        progressDialog = DialogUtils.getProgressDialog(this);
        this.fecha_nacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    getDate();
                }
            }
        });
        this.login.setOnClickListener(this);
        this.registrarme.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iniciar_sesion:
                openLogin();
                break;
            case R.id.registrarme:
                PasswordValidation.pass(this.contrasena.getText().toString());
                if (validar()) {
                    registrar();
                }
                break;
            default:
        }
    }

    private void registrar() {
        MyRestAdapter restAdapter = MyRestAdapter.getLoopBackAdapter(this.getApplicationContext());
        UserRepository userRepo = restAdapter.createRepository(UserRepository.class);
        progressDialog.show();
        MyUser user = userRepo.createUser(this.email.getText().toString(), this.contrasena.getText().toString());
        user.setNombre(this.nombre.getText().toString());
        user.put("nombre",this.nombre.getText().toString());
        user.setApellido(this.apellido.getText().toString());
        user.put("apellido",this.apellido.getText().toString());
        user.setCedula(this.cedula.getText().toString());
        user.put("cedula",this.cedula.getText().toString());

        user.setFecha_nacimiento(fecha_nac);
        user.setDocumentoid("cedula");
        user.put("documentoid","cedula");
        user.save(new VoidCallback(){
            @Override
            public void onSuccess() {
                Log.e("guardado", "user");
                progressDialog.hide();
                alertDialogo("Felicidades, se ha registrado correctamente.",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        limpiarCampos();
                        dialogInterface.cancel();
                        openLogin();
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                StackTraceElement[] trace = t.getStackTrace();
                progressDialog.hide();
                ServerError error = new ServerError(trace);
                if(error.getCodes() != null){
                    Map<String, Object> map = error.getCodes();
                    for (Map.Entry<String, Object> entry : map.entrySet())
                    {
                        String erro = entry.getValue().toString();
                        switch (erro){
                            case "[presence]":
                                erro = "Faltan datos";
                            break;
                            case "[uniqueness]":
                                if(entry.getKey().toString().equals("email")){//email
                                    alertDialogo("El email ya existe, por favor intente con otro.",null);
                                }else{
                                    erro = "Ya esta siendo usado, intente con otro.";
                                }
                            break;
                            default:
                                Log.e("error", "campo: "+entry.getKey() + " error: " + entry.getValue());
                                alertDialogo("Campo: "+entry.getKey() + " Error: " + erro,null);
                            break;
                        }
                        /*if(erro.equals("[presence]")){

                        }else if(erro.equals("[uniqueness]") && entry.getKey().toString().equals("email")){//email
                            alertDialogo("El email ya existe, por favor intente con otro.");
                            return 0;
                        }else if(erro.equals("[uniqueness]")){//email
                            erro = "Ya esta siendo usado, intente con otro.";
                        }*/


                    }

                }else{
                    if(error.getName() != null){
                        String errror = error.getName();
                        if(errror.indexOf("ConnectTimeoutException") != -1){
                            alertDialogo( " Error: Tiempo de espera agotado, verifique su conexion e intente nuevamente.",null);
                        }else{
                            alertDialogo( " Error: " +  error.getName() + "Por favor intente nuevamente, si el problema persiste contacte soporte tecnico.",null);
                        }
                    }else{
                        alertDialogo( " Error desconocido. " +   "Por favor intente nuevamente, si el problema persiste contacte soporte tecnico.",null);
                    }

                }
            }
        });
    }

    private void alertDialogo(String msg,DialogInterface.OnClickListener listener){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setCancelable(true);
        if(listener == null) {
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
        }else{
            builder.setNegativeButton("Ok", listener);
        }
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }
    private void getDate(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                    fecha_nac = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    fecha_nacimiento.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }
            }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    /**/

    private boolean validar() {
        boolean allValid = true;
        for (FormEditText field : new FormEditText[]{this.email, this.contrasena, this.fecha_nacimiento, this.nombre, this.apellido, this.cedula, this.rpcontrasena}) {
            field.setText(field.getText().toString().trim());
            if (field.testValidity() && allValid) {
                allValid = true;
            } else {
                allValid = false;
            }
        }
        return allValid;
    }

    private void limpiarCampos(){
        for (FormEditText field : new FormEditText[]{this.email, this.contrasena, this.fecha_nacimiento, this.nombre, this.apellido, this.cedula, this.rpcontrasena}) {
            field.setText("");
        }
    }

    private void openLogin() {
        finish();
    }
}

/*Usuario
Email
micorreo@gmail.com
Confirmar Email
micorreo@gmail.com
Contraseña
Micontraseña1
Confirmar Contraseña
Micontraseña1
Pregunta de Seguridad
Respuesta de Seguridad
Código Promocional *
Revisa esto :(
DATOS PERSONALES Con ellos protegemos tus transacciones

Nombre
Primer Apellido
Segundo Apellido
Sexo
Fecha de Nacimiento
Nacionalidad
Residencia Fiscal
Documento de Identidad
27989003R*/