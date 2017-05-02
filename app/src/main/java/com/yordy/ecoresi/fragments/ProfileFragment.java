package com.yordy.ecoresi.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.activities.NavDrawerActivity;
import com.yordy.ecoresi.adapters.JugadasAdapter;
import com.yordy.ecoresi.adapters.PostsAdapter;
import com.yordy.ecoresi.adapters.BancaAdapter;
import com.yordy.ecoresi.api.MyRestAdapter;
import com.yordy.ecoresi.api.ServerError;
import com.yordy.ecoresi.api.modelos.user.AnimalitoJugado;
import com.yordy.ecoresi.api.modelos.user.AnimalitoJugadoRepository;
import com.yordy.ecoresi.api.modelos.user.Banca;
import com.yordy.ecoresi.api.modelos.user.BancaRepository;
import com.yordy.ecoresi.api.modelos.user.JugadorBanca;
import com.yordy.ecoresi.api.modelos.user.JugadorBancaRepository;
import com.yordy.ecoresi.api.modelos.user.MyUser;
import com.yordy.ecoresi.classes.Post;
import com.yordy.ecoresi.dialogs.DialogBancaDetalles;
import com.yordy.ecoresi.interfaces.HomeInterface;
import com.yordy.ecoresi.loopback.callbacks.ListCallback;
import com.yordy.ecoresi.loopback.callbacks.ObjectCallback;
import com.yordy.ecoresi.loopback.callbacks.VoidCallback;
import com.yordy.ecoresi.remoting.JsonUtil;
import com.yordy.ecoresi.remoting.adapters.Adapter;
import com.yordy.ecoresi.utils.DialogUtils;
import com.yordy.ecoresi.utils.UserSessionManager;
import com.yordy.ecoresi.utils.Utils;
import com.yordy.ecoresi.views.CustomFontTextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    protected ProgressDialog progressDialog;
    private HomeInterface listener;
    private JugadasAdapter adapter;
    private CustomFontTextView email;
    private CustomFontTextView nombre;
    private CustomFontTextView cedula;
    private CustomFontTextView fecha_nacimiento;
    private CustomFontTextView cambiar_banca;
    private CustomFontTextView banca_detalles;
    private CustomFontTextView banca_jugando;
    @InjectView(R.id.posts)
    RecyclerView posts;
    private CustomFontTextView dinero_disponible;
    private UserSessionManager sesion;
    private CustomFontTextView dinero_jugado;
    private Toolbar toolbar;
    private MyUser currentUser;
    private ArrayList<Banca> listaBanca = new ArrayList();
    private ArrayList<AnimalitoJugado> animalitoJugado = new ArrayList();
    private BancaRepository bancaRepo;
    private JugadorBancaRepository jugadorBancaRepo;
    private AnimalitoJugadoRepository animalitoJugadoRepository;
    private MyRestAdapter restAdapter;
    private int countPeticiones = 0;
    private int limit = 100;
    private int skip = 0;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sesion = new UserSessionManager(getActivity());
        progressDialog = DialogUtils.getProgressDialog(this.getActivity());
        restAdapter = MyRestAdapter.getLoopBackAdapter(this.getActivity());
        bancaRepo = restAdapter.createRepository(BancaRepository.class);
        jugadorBancaRepo = restAdapter.createRepository(JugadorBancaRepository.class);
        animalitoJugadoRepository = restAdapter.createRepository(AnimalitoJugadoRepository.class);
        currentUser = restAdapter.getCurrentUser();
        getBancas();
        if(currentUser.getBancaSelected() != null){
            Log.e("banca selected",Integer.toString(currentUser.getBancaSelected().getIdBanca())+":"+Integer.toString(currentUser.getIdJugador()));
            getBancaSelect(currentUser.getBancaSelected(),currentUser.getIdJugador());
        }else{
            if(currentUser.getCurrent_banca() != 0){
                getCurrentJugadorBancaById(currentUser.getCurrent_banca());
                getAnimalitoJugados(currentUser.getCurrent_banca());
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject((Object) this, rootView);
        toolbar = (Toolbar) ((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Mi cuenta");
        RecyclerViewHeader header = RecyclerViewHeader.fromXml(getActivity(), R.layout.header_profile);

        String user = this.sesion.getUsername();
        //TextView
        this.email = (CustomFontTextView) ButterKnife.findById(header, (int) R.id.email);
        this.nombre = (CustomFontTextView) ButterKnife.findById(header, (int) R.id.nombre);
        this.cedula = (CustomFontTextView) ButterKnife.findById(header, (int) R.id.cedula);
        this.fecha_nacimiento = (CustomFontTextView) ButterKnife.findById(header, (int) R.id.fecha_nacimiento);
        this.banca_jugando = (CustomFontTextView) ButterKnife.findById(header, (int) R.id.banca_jugando);
        this.dinero_disponible =  (CustomFontTextView) ButterKnife.findById(header, (int) R.id.dinero_disponible);
        this.dinero_jugado =  (CustomFontTextView) ButterKnife.findById(header, (int) R.id.dinero_jugado);

        //botones
        this.cambiar_banca = (CustomFontTextView) ButterKnife.findById(header, (int) R.id.cambiar_banca);
        this.cambiar_banca.setOnClickListener(this);
        this.banca_detalles = (CustomFontTextView) ButterKnife.findById(header, (int) R.id.banca_detalles);
        this.banca_detalles.setOnClickListener(this);

        //llenado de datos
        if(currentUser.getCurrent_banca() == 0){
            this.banca_jugando.setText("Por favor seleccione una banca");
            this.dinero_disponible.setText("0 Bs.");
            this.dinero_jugado.setText("0 Bs.");
        }
        this.email.setText(currentUser.getEmail());
        this.nombre.setText(currentUser.getNombre()+" "+currentUser.getApellido());
        ((NavDrawerActivity) this.getActivity()).setNamePerfil(currentUser.getNombre()+" "+currentUser.getApellido());
        this.cedula.setText(currentUser.getCedula());
        this.fecha_nacimiento.setText(Utils.formatDate(currentUser.getFecha_nacimiento()));

        this.adapter = new JugadasAdapter(getActivity(), animalitoJugado);
        this.posts.setLayoutManager(new LinearLayoutManager(getActivity()));
        header.attachTo(this.posts);
        this.posts.setAdapter(this.adapter);

        return rootView;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (HomeInterface) activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cambiar_banca:
                showDialogBancaList();
                break;
            case R.id.banca_detalles:
                showDialog(currentUser.getBancaSelected());
                break;
            default:
                break;
        }
    }

    private void showDialogBancaList(){
        BancaAdapter adapter = new BancaAdapter(this.getActivity(), listaBanca);
        Holder holder = new ListHolder();
        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                Banca banca = (Banca) item;
                if(currentUser.getBancaSelected() != null){
                    if(banca.getIdBanca() != currentUser.getBancaSelected().getIdBanca()){
                        currentUser.setBancaSelected(banca);
                        listener.setFragmentContet(ProfileFragment.newInstance());
                    }
                }else{
                    currentUser.setBancaSelected(banca);
                    listener.setFragmentContet(ProfileFragment.newInstance());
                }
                dialog.dismiss();
            }
        };
        showCompleteDialog(holder, 4, adapter, itemClickListener,true);
    }
    private void showCompleteDialog(Holder holder, int gravity, BancaAdapter adapter, OnItemClickListener itemClickListener,
                                    boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(this.getActivity())
                .setHeader(R.layout.header)
                .setAdapter(adapter)
                .setOnItemClickListener(itemClickListener)
                .setExpanded(expanded)
                .create();
        dialog.show();
    }

    private void getBancas(){
        progressDialog.setMessage("Cargando datos, porfavor espere.");
        progressDialogShow();
        bancaRepo.findAll(new ListCallback<Banca>(){

            @Override
            public void onSuccess(List Banca) {
                progressDialogHide();
                listaBanca = (ArrayList<com.yordy.ecoresi.api.modelos.user.Banca>) Banca;
            }

            @Override
            public void onError(Throwable t) {
                progressDialogHide();
                StackTraceElement[] trace = t.getStackTrace();
                ServerError error = new ServerError(trace);
            }
        });
    }
    private void getBancaSelect(final Banca banca, int IdJugador){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("IdBanca",banca.getIdBanca());
        map.put("IdJugador",IdJugador);
        JugadorBanca jugadorBanca = jugadorBancaRepo.createObject(map);

        //esto es para realizar las jugadas
       /* Map<String, Object> animalito = new HashMap<String, Object>();
        animalito.put("animalito","camello");
        animalito.put("hora","10 am");
        animalito.put("monto","100");
        List<Map> list = new ArrayList<Map>();
        list.add(animalito);
        animalito = new HashMap<String, Object>();
        animalito.put("animalito","leon");
        animalito.put("hora","10 am");
        animalito.put("monto","200");
        list.add(animalito);

        final Map<String,?> parameters = ImmutableMap.of(
                "prueba", "p2",
                "jugadas", list);

        jugadorBancaRepo.invokeStaticMethod(
            "prueba",
            parameters,
            new Adapter.JsonArrayCallback() {
                @Override
                public void onSuccess(final JSONArray response) {
                    Log.e("onSuccessssss",response.toString());
                }

                @Override
                public void onError(final Throwable t) {
                    Log.e("onError", "Cannot list locations.");
                    Log.e("onError", "Cannot list locations.", t);
                }
        });*/


        progressDialog.setMessage("Cargando datos, porfavor espere.");
        progressDialog.show();
        jugadorBanca.save(new Adapter.JsonObjectCallback(){

            @Override
            public void onSuccess(JSONObject JugadorBancaJson) {
                Log.e("jugador Banca",JugadorBancaJson.toString());
                JugadorBanca jugadorBanca = JugadorBancaJson != null
                        ? jugadorBancaRepo.createObject(JsonUtil.fromJson(JugadorBancaJson))
                        : null;
                setTxtSaldo(jugadorBanca);
                restAdapter.setJugadorBanca(jugadorBanca);
                restAdapter.setCurrentBanca(banca);
                banca_jugando.setText(banca.getNombre());
                progressDialog.hide();
                currentUser.setCurrent_banca(jugadorBanca.getIdJugadorBanca());
                currentUser.setId(currentUser.getIdJugador());
                currentUser.setPassword(sesion.getPassword());
                currentUser.put("password",sesion.getPassword());
                getAnimalitoJugados(jugadorBanca.getIdJugadorBanca());
                updateUser();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("errrrr","get ",t);
                progressDialog.hide();
                StackTraceElement[] trace = t.getStackTrace();
                ServerError error = new ServerError(trace);
            }
        },"IdJugadorBanca");
    }
    private void setTxtSaldo(JugadorBanca jugadorBanca){
        this.dinero_disponible.setText(Float.toString(jugadorBanca.getSaldoDisponible())+" Bs.");
        this.dinero_jugado.setText(Float.toString(jugadorBanca.getSaldoPendiente())+" Bs.");
    }
    private void updateUser(){
        currentUser.save(new VoidCallback(){


            @Override
            public void onSuccess() {
                Log.e("ProfileFragment","curent banca guardada");
            }

            @Override
            public void onError(Throwable t) {
                StackTraceElement[] trace = t.getStackTrace();
                ServerError error = new ServerError(trace);
            }
        });
    }
    private void getCurrentJugadorBancaById(int idJugadorBanca){
        progressDialogShow();
        jugadorBancaRepo.findById(idJugadorBanca,new ObjectCallback<JugadorBanca>(){
            @Override
            public void onSuccess(JugadorBanca jugadorBanca) {
                Log.e("ProfileFragment","get getCurrentJugadorBancaById");
                setTxtSaldo(jugadorBanca);
                restAdapter.setJugadorBanca(jugadorBanca);
                getBancaByJugadorBancaById(jugadorBanca.getIdBanca());
            }

            @Override
            public void onError(Throwable t) {
                progressDialogHide();
                StackTraceElement[] trace = t.getStackTrace();
                ServerError error = new ServerError(trace);
            }
        });
    }
    private void getBancaByJugadorBancaById(int idBanca){
        bancaRepo.findById(idBanca,new ObjectCallback<Banca>(){
            @Override
            public void onSuccess(Banca banca) {
                Log.e("ProfileFragment","get getBancaByJugadorBancaById");
                progressDialogHide();
                currentUser.setBancaSelected(banca);
                restAdapter.setCurrentBanca(banca);
                banca_jugando.setText(banca.getNombre());
            }

            @Override
            public void onError(Throwable t) {
                progressDialogHide();
                StackTraceElement[] trace = t.getStackTrace();
                ServerError error = new ServerError(trace);
            }
        });
    }
    private void getAnimalitoJugados(int id){

        progressDialog.setMessage("Cargando datos, porfavor espere.");
        progressDialogShow();
        Map<String, Object> where = new HashMap<String, Object>();
        where.put("filter[where][IdJugadorBanca]",id);
        where.put("filter[order]","IdJugadaAnimalito DESC");
        where.put("filter[limit]",this.limit);
        where.put("filter[skip]",this.skip);
        animalitoJugadoRepository.find(where,new ListCallback<AnimalitoJugado>(){

            @Override
            public void onSuccess(List AnimalitoJugado) {
                Log.e("AnimalitoJugado.size()"," "+AnimalitoJugado.size());
                progressDialogHide();if(AnimalitoJugado.size() > 0){
                    animalitoJugado = (ArrayList<AnimalitoJugado>) AnimalitoJugado;
                    adapter.setList(animalitoJugado);
                }else{
                    animalitoJugado = new ArrayList();
                    adapter.setList(animalitoJugado);
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e("errrrr","get ");
                progressDialogHide();
               StackTraceElement[] trace = t.getStackTrace();
                ServerError error = new ServerError(trace);
            }
        });
    }
    private void progressDialogShow(){
        if(countPeticiones == 0){
            progressDialog.show();
        }
        countPeticiones++;
    }
    private void progressDialogHide(){
        countPeticiones--;
        if(countPeticiones==0){
            progressDialog.hide();
        }

    }
    private void showDialog(Banca banca){
        Bundle args = new Bundle();
        args.putString("nombre",banca.getNombre());
        args.putString("direccion",banca.getDireccion());
        FragmentManager fragmentManager = getFragmentManager();
        DialogBancaDetalles dialog = new DialogBancaDetalles();
        dialog.setArguments(args);
        dialog.setOnDismissListener(new DialogBancaDetalles.OnDismissListener(){

            @Override
            public void onDismiss(DialogBancaDetalles dialogConfimAnimalito) {
            }
        });
        dialog.show(fragmentManager, banca.getNombre());
    }
}
