package com.yordy.ecoresi.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.google.common.collect.ImmutableMap;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.adapters.BancaAdapter;
import com.yordy.ecoresi.adapters.JugadasAdapter;
import com.yordy.ecoresi.adapters.JugadasConfirmAdapter;
import com.yordy.ecoresi.api.MyRestAdapter;
import com.yordy.ecoresi.api.ServerError;
import com.yordy.ecoresi.api.modelos.user.AnimalitoJugado;
import com.yordy.ecoresi.api.modelos.user.AnimalitoJugadoRepository;
import com.yordy.ecoresi.api.modelos.user.Banca;
import com.yordy.ecoresi.api.modelos.user.BancaRepository;
import com.yordy.ecoresi.api.modelos.user.JugadasRepository;
import com.yordy.ecoresi.api.modelos.user.JugadorBanca;
import com.yordy.ecoresi.api.modelos.user.JugadorBancaRepository;
import com.yordy.ecoresi.api.modelos.user.MyUser;
import com.yordy.ecoresi.dialogs.DialogConfimAnimalito;
import com.yordy.ecoresi.interfaces.HomeInterface;
import com.yordy.ecoresi.interfaces.jugadasConfirmAdapterOnClickListenerHack;
import com.yordy.ecoresi.loopback.callbacks.ListCallback;
import com.yordy.ecoresi.loopback.callbacks.ObjectCallback;
import com.yordy.ecoresi.loopback.callbacks.VoidCallback;
import com.yordy.ecoresi.remoting.JsonUtil;
import com.yordy.ecoresi.remoting.adapters.Adapter;
import com.yordy.ecoresi.utils.DialogUtils;
import com.yordy.ecoresi.utils.UserSessionManager;
import com.yordy.ecoresi.views.CustomFontTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class JugadasFragment extends Fragment implements jugadasConfirmAdapterOnClickListenerHack {
    protected ProgressDialog progressDialog;
    private HomeInterface listener;
    private JugadasConfirmAdapter adapter;
    @InjectView(R.id.banca_jugando)
    CustomFontTextView banca_jugando;
    @InjectView(R.id.rvjugadas)
    RecyclerView rvjugadas;
    @InjectView(R.id.dinero_disponible)
    CustomFontTextView dinero_disponible;
    @InjectView(R.id.dinero_jugado)
    CustomFontTextView dinero_jugado;
    private UserSessionManager sesion;
    private Toolbar toolbar;
    private MyUser currentUser;
    private MyRestAdapter restAdapter;
    private List<Map> jugadas = new ArrayList<Map>();
    private JugadasRepository jugadasRepo;

    public static JugadasFragment newInstance() {
        return new JugadasFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.sesion = new UserSessionManager(getActivity());
        progressDialog = DialogUtils.getProgressDialog(this.getActivity());
        restAdapter = MyRestAdapter.getLoopBackAdapter(this.getActivity());
        jugadasRepo = restAdapter.createRepository(JugadasRepository.class);
        currentUser = restAdapter.getCurrentUser();
        jugadas =  currentUser.getEnviarJugadas();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_jugadas, container, false);
        ButterKnife.inject((Object) this, rootView);
        toolbar = (Toolbar) ((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Anterior");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setFragmentContet(ReportsFragment.newInstance());
            }
        });

        //llenado de datos
        if(currentUser.getCurrent_banca() == 0){
            this.banca_jugando.setText("Por favor seleccione una banca");
            this.dinero_disponible.setText("0 Bs.");
            this.dinero_jugado.setText("0 Bs.");
        }else{
            this.banca_jugando.setText(currentUser.getBancaSelected().getNombre());
            float saldoDis = this.restAdapter.getJugadorBanca().getSaldoDisponible() - this.restAdapter.getJugadorBanca().getSaldoPendiente();
            this.dinero_disponible.setText(Float.toString(saldoDis));
            getMontoJugadas();
        }

        this.adapter = new JugadasConfirmAdapter(this.getActivity());
        this.adapter.setOnClickListenerHack(this);
        this.adapter.setFragmentManager(this.getFragmentManager());
        this.rvjugadas.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.rvjugadas.setAdapter(this.adapter);

        return rootView;
    }

    private void getMontoJugadas(){
        float montoJugado = 0;
        for (Map element : jugadas) {
            float monto = Float.parseFloat((String) element.get(DialogConfimAnimalito.keyMonto));
            montoJugado += monto;
        }
        this.dinero_jugado.setText(Float.toString(montoJugado));
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (HomeInterface) activity;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_jugadas, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.jugar) {
            Log.e("envia jugadas","al servidor");
            jugadas =  currentUser.getEnviarJugadas();
            float montoJugado = 0;
            for (Map element : jugadas) {
                float monto = Float.parseFloat((String) element.get(DialogConfimAnimalito.keyMonto));
                montoJugado += monto;
            }
            float saldoDis = this.restAdapter.getJugadorBanca().getSaldoDisponible() - this.restAdapter.getJugadorBanca().getSaldoPendiente();
            Log.e("ddddddd",Float.toString(saldoDis));
            Log.e("dddddddd",Float.toString(montoJugado));
            if(saldoDis >= montoJugado){
                progressDialog.show();
                final Map<String,?> parameters = ImmutableMap.of(
                        "IdJugadorBanca", currentUser.getCurrent_banca(),
                        "monto", montoJugado,
                        "jugadas", jugadas);

                jugadasRepo.invokeStaticMethod(
                    "jugar",
                    parameters,
                    new Adapter.JsonObjectCallback() {
                        @Override
                        public void onSuccess(final JSONObject response) {
                            Log.e("onSuccessssss",response.toString());
                            progressDialog.dismiss();
                            currentUser.setEnviarJugadas(new ArrayList<Map>());
                            adapter.clear();
                            listener.setFragmentContet(ProfileFragment.newInstance());
                            try {
                                alertDialogo(response.getString("mensaje").toString(),null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(final Throwable t) {//at error.error({"error":{"statusCode":460,"name":"Error","message":"Su saldo no es suficiente"}}:0)
                            progressDialog.dismiss();
                            StackTraceElement[] trace = t.getStackTrace();
                            ServerError error = new ServerError(trace);
                            if (error.getStatusCode() != null) {
                                String erro = error.getStatusCode().toString();
                                switch (erro) {
                                    case "460":
                                        alertDialogo(error.getMessage(), null);
                                        break;
                                    case "461":
                                        alertDialogo(error.getMessage(), null);
                                        break;
                                    case "453":
                                        alertDialogo(error.getMessage(), null);
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
                            Log.e("onError", "Cannot list locations.", t);
                        }
                    });
            }else{
                alertDialogo("Saldo insuficiente para realizar estas jugadas.",null);
            }

            return true;
        }else if(id == R.id.del){
            alertDialogo("¿Esta seguro que desea eliminar estas jugadas?.",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            },new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    currentUser.setEnviarJugadas(new ArrayList<Map>());
                    adapter.clear();
                    listener.setFragmentContet(ReportsFragment.newInstance());
                    dialogInterface.cancel();
                }
            });
        }

        return super.onOptionsItemSelected(item);
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

    private void alertDialogo(String msg, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener listener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
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

    @Override
    public void RemoveOnClickListener(int position) {
        jugadas =  currentUser.getEnviarJugadas();
        getMontoJugadas();
    }

    @Override
    public void UpdateOnClickListener(int position) {
        jugadas =  currentUser.getEnviarJugadas();
        getMontoJugadas();
    }
}
