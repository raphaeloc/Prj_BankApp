package com.example.prj_bankapp.ui.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;


import com.example.prj_bankapp.api.BankAPI;
import com.example.prj_bankapp.model.listmodel.Statement;
import com.example.prj_bankapp.model.listmodel.StatementList;
import com.example.prj_bankapp.model.usermodel.User;
import com.example.prj_bankapp.model.usermodel.UserAccount;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prj_bankapp.util.LoginValidator.isValidPassword;
import static com.example.prj_bankapp.util.LoginValidator.validarCpf;
import static com.example.prj_bankapp.util.LoginValidator.validarEmail;
import static com.example.prj_bankapp.util.RetroFitInitializer.getRetrofitInstance;

public class UserViewModel extends ViewModel {

    public MutableLiveData<UserAccount> userAccount = new MutableLiveData<>();

    public MutableLiveData<UserAccount> getUser() {
        return userAccount;
    }

    public MutableLiveData<List<StatementList>> statementList = new MutableLiveData<>();

    public MutableLiveData<List<StatementList>> getStatementList() {
        return statementList;
    }

    public MutableLiveData<String> mensagem = new MutableLiveData<>();

    public MutableLiveData<String> getMensagem() {
        return mensagem;
    }

    // Do login
    public void doLogin(String user, String password){
        doingLogin(user, password);
    }

    private void doingLogin(String user, String password) {

        BankAPI bankAPI = getRetrofitInstance().create(BankAPI.class);
        Call<User> call = bankAPI.login("teste", "teste");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();

                    userAccount.setValue(user.getUserAccount());
                }
                else{
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    /**
     * Statement
     */
    public void getList(String id){
        gettingList(id);
    }

    private void gettingList(String id) {
        BankAPI bankAPI = getRetrofitInstance().create(BankAPI.class);
        Call<Statement> call = bankAPI.list(id);

        call.enqueue(new Callback<Statement>() {
            @Override
            public void onResponse(Call<Statement> call, Response<Statement> response) {
                if (response.isSuccessful()){
                    Statement slist = response.body();

                    List<StatementList> lista = slist.getStatementList();

                    statementList.setValue(lista);
                }
            }

            @Override
            public void onFailure(Call<Statement> call, Throwable t) {

            }
        });
    }


    /**
     * Validar os campos de login
     * @param user
     * @param password
     */
    public void validarCampos(String user, String password) {

        if(checkUser(user)){
            Log.d("Abacaxi", "Entrou no validar usuario como true");

            if(checkPassword(password)){
                Log.d("Abacaxi", "Entrou no validar senha como true");
                mensagem.setValue("");
            }
            else{
                Log.d("Abacaxi", "Entrou no validar senha como False");
                mensagem.setValue("Senha no formato incorreto.");
            }
        }
        else{
            Log.d("Abacaxi", "Entrou no validar usuario como false");
            mensagem.setValue("Usuario no formato incorreto.");
        }
    }

    private boolean checkUser(String user){

        if(validarEmail(user)){
            Log.d("Abacaxi", "Contem @");
            return true;
        }

        String cpf = user.replace(".", "");
        if(validarCpf(cpf)){
            Log.d("Abacaxi", "Contem cpf");
            return true;
        }
        else{
            Log.d("Abacaxi", "Usuario formato incorreto.");
            return false;
        }
    }

    private boolean checkPassword(String password){
        //
        if(isValidPassword(password)){
            Log.d("Abacaxi", "Senha valida" + password);
            return true;
        }
        else{
            Log.d("Abacaxi", "Senha invalida" + password);
            return false;
        }
    }
}
