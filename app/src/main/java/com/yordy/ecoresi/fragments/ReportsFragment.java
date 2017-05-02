package com.yordy.ecoresi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import com.yordy.ecoresi.R;
import com.yordy.ecoresi.adapters.ReportAdapter;
import com.yordy.ecoresi.api.MyRestAdapter;
import com.yordy.ecoresi.api.modelos.user.MyUser;
import com.yordy.ecoresi.classes.Report;
import com.yordy.ecoresi.dialogs.DialogConfimAnimalito;
import com.yordy.ecoresi.interfaces.HomeInterface;

public class ReportsFragment extends Fragment implements View.OnClickListener {
    private TextView mCounter = null;
    private Button notifiHulla;
    private MyRestAdapter restAdapter;
    private MyUser currentUser;
    private Toolbar toolbar;
    private HomeInterface listener;
    @InjectView(R.id.animalito_0)
    ImageButton animalito_0;
    @InjectView(R.id.animalito_00)
    ImageButton animalito_00;
    @InjectView(R.id.animalito_1)
    ImageButton animalito_1;
    @InjectView(R.id.animalito_2)
    ImageButton animalito_2;
    @InjectView(R.id.animalito_3)
    ImageButton animalito_3;
    @InjectView(R.id.animalito_4)
    ImageButton animalito_4;
    @InjectView(R.id.animalito_5)
    ImageButton animalito_5;
    @InjectView(R.id.animalito_6)
    ImageButton animalito_6;
    @InjectView(R.id.animalito_7)
    ImageButton animalito_7;
    @InjectView(R.id.animalito_8)
    ImageButton animalito_8;
    @InjectView(R.id.animalito_9)
    ImageButton animalito_9;
    @InjectView(R.id.animalito_10)
    ImageButton animalito_10;
    @InjectView(R.id.animalito_11)
    ImageButton animalito_11;
    @InjectView(R.id.animalito_12)
    ImageButton animalito_12;
    @InjectView(R.id.animalito_13)
    ImageButton animalito_13;
    @InjectView(R.id.animalito_14)
    ImageButton animalito_14;
    @InjectView(R.id.animalito_15)
    ImageButton animalito_15;
    @InjectView(R.id.animalito_16)
    ImageButton animalito_16;
    @InjectView(R.id.animalito_17)
    ImageButton animalito_17;
    @InjectView(R.id.animalito_18)
    ImageButton animalito_18;
    @InjectView(R.id.animalito_19)
    ImageButton animalito_19;
    @InjectView(R.id.animalito_20)
    ImageButton animalito_20;
    @InjectView(R.id.animalito_21)
    ImageButton animalito_21;
    @InjectView(R.id.animalito_22)
    ImageButton animalito_22;
    @InjectView(R.id.animalito_23)
    ImageButton animalito_23;
    @InjectView(R.id.animalito_24)
    ImageButton animalito_24;
    @InjectView(R.id.animalito_25)
    ImageButton animalito_25;
    @InjectView(R.id.animalito_26)
    ImageButton animalito_26;
    @InjectView(R.id.animalito_27)
    ImageButton animalito_27;
    @InjectView(R.id.animalito_28)
    ImageButton animalito_28;
    @InjectView(R.id.animalito_29)
    ImageButton animalito_29;
    @InjectView(R.id.animalito_30)
    ImageButton animalito_30;
    @InjectView(R.id.animalito_31)
    ImageButton animalito_31;
    @InjectView(R.id.animalito_32)
    ImageButton animalito_32;
    @InjectView(R.id.animalito_33)
    ImageButton animalito_33;
    @InjectView(R.id.animalito_34)
    ImageButton animalito_34;
    @InjectView(R.id.animalito_35)
    ImageButton animalito_35;
    @InjectView(R.id.animalito_36)
    ImageButton animalito_36;

    public static ReportsFragment newInstance() {
        return new ReportsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        restAdapter = MyRestAdapter.getLoopBackAdapter(this.getActivity());
        currentUser = restAdapter.getCurrentUser();
        if(mCounter != null){
            mCounter.setText(Integer.toString(currentUser.countJugadas()));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reports, container, false);
        ButterKnife.inject((Object) this, rootView);
        animalito_0.setOnClickListener(this);
        animalito_00.setOnClickListener(this);
        animalito_1.setOnClickListener(this);
        animalito_2.setOnClickListener(this);
        animalito_3.setOnClickListener(this);
        animalito_4.setOnClickListener(this);
        animalito_5.setOnClickListener(this);
        animalito_6.setOnClickListener(this);
        animalito_7.setOnClickListener(this);
        animalito_8.setOnClickListener(this);
        animalito_9.setOnClickListener(this);
        animalito_10.setOnClickListener(this);
        animalito_11.setOnClickListener(this);
        animalito_12.setOnClickListener(this);
        animalito_13.setOnClickListener(this);
        animalito_14.setOnClickListener(this);
        animalito_15.setOnClickListener(this);
        animalito_16.setOnClickListener(this);
        animalito_17.setOnClickListener(this);
        animalito_18.setOnClickListener(this);
        animalito_19.setOnClickListener(this);
        animalito_20.setOnClickListener(this);
        animalito_21.setOnClickListener(this);
        animalito_22.setOnClickListener(this);
        animalito_23.setOnClickListener(this);
        animalito_24.setOnClickListener(this);
        animalito_25.setOnClickListener(this);
        animalito_26.setOnClickListener(this);
        animalito_27.setOnClickListener(this);
        animalito_28.setOnClickListener(this);
        animalito_29.setOnClickListener(this);
        animalito_30.setOnClickListener(this);
        animalito_31.setOnClickListener(this);
        animalito_32.setOnClickListener(this);
        animalito_33.setOnClickListener(this);
        animalito_34.setOnClickListener(this);
        animalito_35.setOnClickListener(this);
        animalito_36.setOnClickListener(this);
        toolbar = (Toolbar) ((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Seleccione");


        return rootView;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.animalito_0:
                showDialog("Delfin");
                break;
            case R.id.animalito_00:
                showDialog("Ballena");
                break;
            case R.id.animalito_1:
                showDialog("Carnero");
                break;
            case R.id.animalito_2:
                showDialog("Toro");
                break;
            case R.id.animalito_3:
                showDialog("Ciempies");
                break;
            case R.id.animalito_4:
                showDialog("Alacran");
                break;
            case R.id.animalito_5:
                showDialog("Leon");
                break;
            case R.id.animalito_6:
                showDialog("Rana");
                break;
            case R.id.animalito_7:
                showDialog("Perico");
                break;
            case R.id.animalito_8:
                showDialog("Raton");
                break;
            case R.id.animalito_9:
                showDialog("Aguila");
                break;
            case R.id.animalito_10:
                showDialog("Tigre");
                break;
            case R.id.animalito_11:
                showDialog("Gato");
                break;
            case R.id.animalito_12:
                showDialog("Caballo");
                break;
            case R.id.animalito_13:
                showDialog("Mono");
                break;
            case R.id.animalito_14:
                showDialog("Paloma");
                break;
            case R.id.animalito_15:
                showDialog("Zorro");
                break;
            case R.id.animalito_16:
                showDialog("Oso");
                break;
            case R.id.animalito_17:
                showDialog("Pavo");
                break;
            case R.id.animalito_18:
                showDialog("Burro");
                break;
            case R.id.animalito_19:
                showDialog("Chivo");
                break;
            case R.id.animalito_20:
                showDialog("Cochino");
                break;
            case R.id.animalito_21:
                showDialog("Gallo");
                break;
            case R.id.animalito_22:
                showDialog("Camello");
                break;
            case R.id.animalito_23:
                showDialog("Zebra");
                break;
            case R.id.animalito_24:
                showDialog("Iguana");
                break;
            case R.id.animalito_25:
                showDialog("Gallina");
                break;
            case R.id.animalito_26:
                showDialog("Vaca");
                break;
            case R.id.animalito_27:
                showDialog("Perro");
                break;
            case R.id.animalito_28:
                showDialog("Zamuro");
                break;
            case R.id.animalito_29:
                showDialog("Elefante");
                break;
            case R.id.animalito_30:
                showDialog("Caiman");
                break;
            case R.id.animalito_31:
                showDialog("Lapa");
                break;
            case R.id.animalito_32:
                showDialog("Ardilla");
                break;
            case R.id.animalito_33:
                showDialog("Pescado");
                break;
            case R.id.animalito_34:
                showDialog("falta");
                break;
            case R.id.animalito_35:
                showDialog("falta1");
                break;
            case R.id.animalito_36:
                showDialog("falta2");
                break;
            default:
                break;
        }
    }

    private void showDialog(String animalito){
        Log.e("DialogConfimAnimalito", animalito);
        Bundle args = new Bundle();
        args.putString(DialogConfimAnimalito.keyOperacion,DialogConfimAnimalito.operacionGuardar);
        args.putString(DialogConfimAnimalito.keyAnimalito,animalito);
        FragmentManager fragmentManager = getFragmentManager();
        DialogConfimAnimalito dialog = new DialogConfimAnimalito();
        dialog.setArguments(args);
        dialog.setOnDismissListener(new DialogConfimAnimalito.OnDismissListener(){

            @Override
            public void onDismiss(DialogConfimAnimalito dialogConfimAnimalito) {
                mCounter.setText(Integer.toString(currentUser.countJugadas()));
            }
        });
        dialog.show(fragmentManager, animalito);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_person, menu);
//notifiHulla

        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.huella).getActionView();
        mCounter = (TextView) badgeLayout.findViewById(R.id.notifcation_textview);
        notifiHulla = (Button) badgeLayout.findViewById(R.id.notifcation_button_chat);
        /*notifiHulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, HuellasActivity.class));
            }
        });*/
        mCounter.setVisibility(View.VISIBLE);
        mCounter.setText(Integer.toString(currentUser.countJugadas()));
        //mCounter.setVisibility(View.GONE);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sig) {
            this.listener.setFragmentContet(JugadasFragment.newInstance());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (HomeInterface) activity;
    }
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
