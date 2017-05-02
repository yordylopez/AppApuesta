package com.yordy.ecoresi.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andreabaccega.formedittextvalidator.AlphaNumericValidator;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.OrValidator;
import com.andreabaccega.widget.FormEditText;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.api.MyRestAdapter;
import com.yordy.ecoresi.api.ServerError;
import com.yordy.ecoresi.api.modelos.user.MyUser;
import com.yordy.ecoresi.api.modelos.user.UserRepository;
import com.yordy.ecoresi.loopback.AccessToken;
import com.yordy.ecoresi.utils.DialogUtils;
import com.yordy.ecoresi.utils.UserSessionManager;
import com.yordy.ecoresi.views.CustomFontFormEditText;
import com.yordy.ecoresi.views.CustomFontTextView;

public class LoginActivity extends ActionBarActivity implements OnClickListener {
    @InjectView(R.id.contrasena)
    CustomFontFormEditText contrasena;
    @InjectView(R.id.email)
    CustomFontFormEditText email;
    @InjectView(R.id.iniciar_sesion)
    Button login;
    @InjectView(R.id.registrarse)
    CustomFontTextView registrarse;
    protected ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_login);
        ButterKnife.inject((Activity) this);
        progressDialog = DialogUtils.getProgressDialog(this);
        this.registrarse.setOnClickListener(this);
        this.login.setOnClickListener(this);
        this.email.addValidator(new OrValidator("Debes ingresar un email valido", new AlphaNumericValidator(null), new EmailValidator(null)));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iniciar_sesion:
                if (validar()) {
                    try{
                        progressDialog.show();
                        doLoging();
                    }catch(Exception ex){

                    }

                }
                break;
            case R.id.registrarse:
                openRegistro();
                break;
            default:
                break;
        }
    }

    private void doLoging() {
        final MyRestAdapter restAdapter = MyRestAdapter.getLoopBackAdapter(this.getApplicationContext());
        UserRepository userRepo = restAdapter.createRepository(UserRepository.class);
        userRepo.loginUser(this.email.getText().toString(), this.contrasena.getText().toString(), new UserRepository.LoginCallback() {
            @Override
            public void onSuccess(AccessToken token, MyUser currentUser) {
                progressDialog.dismiss();
                Log.e("guardado", token.getUserId() + ":" + currentUser.getNombre());
                restAdapter.setCurrentUser(currentUser);
                UserSessionManager sesion = new UserSessionManager(getApplicationContext());
                sesion.setCurrentUser(currentUser);
                sesion.setUsername(email.getText().toString().trim());
                sesion.setPassword(contrasena.getText().toString());
                sesion.setSeguidores("34");
                sesion.setSiguiendo("45");
                sesion.setEstado("Todo bien");
                sesion.setUrlFoto("http://s3-us-west-2.amazonaws.com/s.cdpn.io/6083/profile/profile-512_1.jpg");
                startActivity(new Intent(getApplicationContext(), NavDrawerActivity.class));
                finish();
            }

            @Override
            public void onError(Throwable t) {
                progressDialog.dismiss();
                StackTraceElement[] trace = t.getStackTrace();
                ServerError error = new ServerError(trace);
                //Log.e("onError", error.getCode());
                if (error.getStatusCode() != null) {
                    String erro = error.getStatusCode().toString();
                    switch (erro) {
                        case "400":
                            if (error.getMessage().equals("username o email es obligatorio")) {
                                alertDialogo("Debe de ingresar el email.", null);
                            }
                            break;
                        case "401":
                            if (error.getCode() != null) {
                                if (error.getCode().equals("LOGIN_FAILED")) {
                                    alertDialogo("Email o contraseña invalidos.", null);
                                }
                            }
                            break;
                        default:
                            alertDialogo(" Error: " + error.getName() + "Por favor intente nuevamente, si el problema persiste contacte soporte tecnico.", null);
                            break;
                    }
                } else {
                    if (error.getName() != null) {
                        String errror = error.getName();
                        if (errror.indexOf("ConnectTimeoutException") != -1) {
                            alertDialogo(" Error: Tiempo de espera agotado, verifique su conexion e intente nuevamente.", null);
                        } else {
                            alertDialogo(" Error: " + error.getName() + "Por favor intente nuevamente, si el problema persiste contacte soporte tecnico.", null);
                        }
                    } else {
                        alertDialogo(" Error desconocido. " + "Por favor intente nuevamente, si el problema persiste contacte soporte tecnico.", null);
                    }
                }

            }
        });
    }

    private void openRegistro() {
        startActivity(new Intent(this, RegistroActivity.class));
    }

    private void alertDialogo(String msg, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setCancelable(true);
        if (listener == null) {
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        } else {
            builder.setNegativeButton("Ok", listener);
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean validar() {
        boolean allValid = true;
        for (FormEditText field : new FormEditText[]{this.email, this.contrasena}) {
            field.setText(field.getText().toString().trim());
            if (field.testValidity() && allValid) {
                allValid = true;
            } else {
                allValid = false;
            }
        }
        return allValid;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
