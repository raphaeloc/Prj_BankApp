package com.example.prj_bankapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prj_bankapp.api.BankAPI;
import com.example.prj_bankapp.ui.model.UserViewModel;
import com.example.prj_bankapp.util.Constantes;
import com.example.prj_bankapp.util.ToolBox;
import com.example.prj_bankapp.R;
import com.example.prj_bankapp.model.User;
import com.example.prj_bankapp.model.UserAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.prj_bankapp.util.Constantes.WEBSERVICE;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText et_user, et_password;
    private String mensagem;
    private Context context;
    private Retrofit retrofit;
    private UserAccount userAccount;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarVariaveis();
        inicializarAcao();
    }

    private void inicializarVariaveis() {
        context = LoginActivity.this;
        btn_login = findViewById(R.id.login_btn_login);
        et_password = findViewById(R.id.login_et_password);
        et_user = findViewById(R.id.login_et_user);
        //
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        //
        retrofit = new Retrofit.Builder()
                .baseUrl(WEBSERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void inicializarAcao() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, password;
                user = String.valueOf(et_user.getText());
                password = String.valueOf(et_password.getText());
                //
                viewModel.validarCampos(user, password);
                //
                if (mensagem.equals("")) {
                    viewModel.doLogin(user, password, retrofit);
                } else {
                    displayMessage("Preencha os campos corretamente.");
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<UserAccount>() {
            @Override
            public void onChanged(@Nullable UserAccount userAccount) {
                chamarTela(userAccount);
            }
        });

        viewModel.getMensagem().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                mensagem = message;
            }
        });
    }

    private void chamarTela(UserAccount userAccount) {
        Intent mIntent = new Intent(context, InicialActivity.class);
        //
        Bundle mBundle = new Bundle();
        //
        mBundle.putInt(Constantes.USERID, userAccount.getUserId());
        mBundle.putString(Constantes.NAME, userAccount.getName());
        mBundle.putString(Constantes.BANKACCOUNT, userAccount.getBankAccount());
        mBundle.putString(Constantes.AGENCY, userAccount.getAgency());
        mBundle.putDouble(Constantes.BALANCE, userAccount.getBalance());
        //
        mIntent.putExtra("bundle", mBundle);
        //
        startActivity(mIntent);
        //
        finish();
    }

    private void displayMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
