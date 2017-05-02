package com.yordy.ecoresi.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;

import com.google.common.collect.ImmutableMap;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.adapters.JugadasConfirmAdapter;
import com.yordy.ecoresi.api.MyRestAdapter;
import com.yordy.ecoresi.api.ServerError;
import com.yordy.ecoresi.api.modelos.user.JugadasRepository;
import com.yordy.ecoresi.api.modelos.user.MyUser;
import com.yordy.ecoresi.dialogs.DialogConfimAnimalito;
import com.yordy.ecoresi.interfaces.HomeInterface;
import com.yordy.ecoresi.interfaces.jugadasConfirmAdapterOnClickListenerHack;
import com.yordy.ecoresi.remoting.adapters.Adapter;
import com.yordy.ecoresi.utils.DialogUtils;
import com.yordy.ecoresi.utils.UserSessionManager;
import com.yordy.ecoresi.utils.Utils;
import com.yordy.ecoresi.views.CustomFontFormEditText;
import com.yordy.ecoresi.views.CustomFontTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MisDatosFragment extends Fragment {
    protected ProgressDialog progressDialog;
    @InjectView(R.id.contrasena)
    CustomFontFormEditText contrasena;
    @InjectView(R.id.rpcontrasena)
    CustomFontFormEditText rpcontrasena;
    @InjectView(R.id.fecha_nacimiento)
    CustomFontFormEditText fecha_nacimiento;
    @InjectView(R.id.email)
    CustomFontFormEditText email;
    @InjectView(R.id.nombre)
    CustomFontFormEditText nombre;
    @InjectView(R.id.apellido)
    CustomFontFormEditText apellido;
    @InjectView(R.id.update)
    Button update;
    private Toolbar toolbar;
    private MyUser currentUser;
    private MyRestAdapter restAdapter;
    String fecha_nac = null;
    private int mYear, mMonth, mDay;

    public static MisDatosFragment newInstance() {
        return new MisDatosFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        progressDialog = DialogUtils.getProgressDialog(this.getActivity());
        restAdapter = MyRestAdapter.getLoopBackAdapter(this.getActivity());
        currentUser = restAdapter.getCurrentUser();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_datos, container, false);
        ButterKnife.inject((Object) this, rootView);
        toolbar = (Toolbar) ((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Mis datos");

        if(this.currentUser != null){
            this.nombre.setText(this.currentUser.getNombre().toString());
            this.apellido.setText(this.currentUser.getApellido().toString());
            this.fecha_nacimiento.setText(Utils.formatDate(currentUser.getFecha_nacimiento()));
            this.email.setText(this.currentUser.getEmail().toString());
            this.fecha_nacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        getDate();
                    }
                }
            });
        }


        return rootView;
    }
    private void getDate(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        fecha_nac = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        fecha_nacimiento.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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

}
