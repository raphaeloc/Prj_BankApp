package com.example.prj_bankapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.prj_bankapp.ui.viewmodel.UserViewModel;
import com.example.prj_bankapp.util.Constantes;
import com.example.prj_bankapp.R;
import com.example.prj_bankapp.model.usermodel.UserAccount;

import static com.example.prj_bankapp.util.ToolBox.displayMessage;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private EditText et_user, et_password;
    private ProgressBar pb_load;
    private String mensagem = "";
    private Context context;
    private UserViewModel viewModel;
    private SharedPreferences preferences;


    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

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
        btn_login.setEnabled(true);
        et_password = findViewById(R.id.login_et_password);
        et_user = findViewById(R.id.login_et_user);
        pb_load = findViewById(R.id.login_pb_load);
        //
        setLoadInvisible();
        //
        preferences = getSharedPreferences(Constantes.ARQUIVODEPREFERENCIAS, 0);
        //
        if(preferences.contains(Constantes.NAME)){
            et_user.setText(preferences.getString(Constantes.NAME, ""));
        }
        else{
            et_user.setText("");
        }
        //
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void inicializarAcao() {
        viewModel.getMensagem().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                setMensagem(message);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setEnabled(false);
                setLoadVisible();
                SharedPreferences.Editor editor = preferences.edit();
                //
                String user, password;
                user = String.valueOf(et_user.getText());
                password = String.valueOf(et_password.getText());
                //
                viewModel.validarCampos(user, password);
                //
                if (mensagem.equals("")) {
                    //
                    viewModel.doLogin(user, password);
                    setLoadInvisible();
                    editor.putString(Constantes.NAME, user);
                    editor.commit();
                } else {
                    //
                    setLoadInvisible();
                    //
                    et_user.setBackgroundResource(R.drawable.shape);
                    et_password.setBackgroundResource(R.drawable.shape);
                    //
                    displayMessage(context, mensagem);
                    switch (mensagem) {
                        case "Senha no formato incorreto.":
                            et_password.setBackgroundResource(R.drawable.shapeerror);
                            et_password.findFocus();
                            break;

                        case "Usuario no formato incorreto.":
                            et_user.setBackgroundResource(R.drawable.shapeerror);
                            et_user.findFocus();
                            break;

                        default:
                            break;
                    }
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<UserAccount>() {
            @Override
            public void onChanged(@Nullable UserAccount userAccount) {
                chamarTela(userAccount);
            }
        });
    }

    private void chamarTela(UserAccount userAccount) {
        Intent mIntent = new Intent(context, InicialActivity.class);
        //
        Bundle mBundle = new Bundle();
        //
        mBundle.putSerializable("teste", userAccount);
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

    private void setLoadInvisible(){
        pb_load.setVisibility(View.INVISIBLE);
    }

    private void setLoadVisible(){
        pb_load.setVisibility(View.VISIBLE);
    }
}
