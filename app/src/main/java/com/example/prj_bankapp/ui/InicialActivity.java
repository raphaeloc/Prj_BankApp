package com.example.prj_bankapp.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prj_bankapp.adapter.StatementAdapter;
import com.example.prj_bankapp.model.listmodel.StatementList;
import com.example.prj_bankapp.ui.viewmodel.UserViewModel;
import com.example.prj_bankapp.util.Constantes;
import com.example.prj_bankapp.R;

import java.util.List;

public class InicialActivity extends AppCompatActivity {

    private Button btn_back;
    private RecyclerView rv_list;
    private Context context;
    private TextView tv_name, tv_agency, tv_balance;
    private UserViewModel viewModel;
    private Bundle mBundle;
    private StatementAdapter statementAdapter;
    //
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        inicializarVariaveis();
        inicializarAcao();
    }

    private void inicializarVariaveis() {
        context = InicialActivity.this;
        //
        tv_agency = findViewById(R.id.inicial_tv_agency);
        tv_name = findViewById(R.id.inicial_tv_name);
        tv_balance = findViewById(R.id.inicial_tv_balance);
        btn_back =  findViewById(R.id.inicial_btn_back);
        rv_list = findViewById(R.id.inicial_rv_list);
        //
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        //
        Intent mIntent = getIntent();
        mBundle = mIntent.getBundleExtra("bundle");
        //
        viewModel.getList(String.valueOf(mBundle.getInt(Constantes.USERID)));
        //
        statementAdapter = new StatementAdapter(R.layout.celula, context);
        //
        viewModel.getStatementList().observe(this, new Observer<List<StatementList>>() {
            @Override
            public void onChanged(@Nullable List<StatementList> statementLists) {
                statementAdapter.setDados(statementLists);
            }
        });
        //
        rv_list.setAdapter(statementAdapter);
        layoutManager = new LinearLayoutManager(context);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(layoutManager);
        //
        setUserData();
        //
    }

    private void inicializarAcao() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }


    private void back(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        Log.d("Abacaxi", "Builder alert");
        alertDialog.setTitle("Sair");
        Log.d("Abacaxi", "Title setado");
        alertDialog.setMessage("Deseja sair da aplicação?");
        Log.d("Abacaxi", "Message setado");
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton("Não", null);
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mIntent = new Intent(context, LoginActivity.class);
                //
                startActivity(mIntent);
                //
                finish();
            }
        });
        alertDialog.show();
    }

    private void setUserData(){
        tv_name.setText(mBundle.getString(Constantes.NAME));
        String teste = mBundle.getString(Constantes.AGENCY);
        tv_agency.setText(mBundle.getString(Constantes.AGENCY) + " / " + mBundle.getString(Constantes.BANKACCOUNT));
        tv_balance.setText(String.valueOf(mBundle.getDouble(Constantes.BALANCE)));
    }
}
