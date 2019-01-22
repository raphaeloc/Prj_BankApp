package com.example.prj_bankapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prj_bankapp.util.Constantes;
import com.example.prj_bankapp.R;

public class InicialActivity extends AppCompatActivity {

    private Button btn_back;
    private Context context;
    private TextView tv_name, tv_agency, tv_balance;

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
        //
        btn_back =  findViewById(R.id.inicial_btn_back);
        //
        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getBundleExtra("bundle");
        //
        tv_name.setText(mBundle.getString(Constantes.NAME));
        String teste = mBundle.getString(Constantes.AGENCY);
        tv_agency.setText(mBundle.getString(Constantes.AGENCY) + " / " + mBundle.getString(Constantes.BANKACCOUNT));
        tv_balance.setText(String.valueOf(mBundle.getDouble(Constantes.BALANCE)));
    }

    private void inicializarAcao() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "teste", Toast.LENGTH_SHORT).show();
                alert("Sair", "Deseja sair da aplicação?");
            }
        });
    }

    private void alert(String title, String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        Log.d("Abacaxi", "Builder alert");
        alertDialog.setTitle(title);
        Log.d("Abacaxi", "Title setado");
        alertDialog.setMessage(message);
        Log.d("Abacaxi", "Message setado");
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton("Não", null);
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Abacaxi", "Entrou no on click");
                back();
            }
        });
        alertDialog.show();
    }

    private void back(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(context, LoginActivity.class);
        //
        startActivity(mIntent);
        //
        finish();
    }
}
