package com.example.prj_bankapp.ui.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;


import com.example.prj_bankapp.api.BankAPI;
import com.example.prj_bankapp.model.User;
import com.example.prj_bankapp.model.UserAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserViewModel extends ViewModel {

    public MutableLiveData<UserAccount> userAccount = new MutableLiveData<>();

    public MutableLiveData<UserAccount> getUser() {
        return userAccount;
    }

    public MutableLiveData<String> mensagem = new MutableLiveData<>();

    public MutableLiveData<String> getMensagem() {
        return mensagem;
    }

    // Do
    public void doLogin(String user, String password, Retrofit retrofit){
        doingLogin(user, password, retrofit);
    }

    private void doingLogin(String user, String password, Retrofit retrofit) {
        BankAPI bankAPI = retrofit.create(BankAPI.class);
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

    public void validarCampos(String user, String password) {
        if(user.contains("@")){
            mensagem.setValue("");
            Log.d("Abacaxi", "Contem @");
            return;
        }

        String usernew = user.replace(".", "");
        if(usernew.matches("[0-9]+") && usernew.length() == 11){
            mensagem.setValue("");
            Log.d("Abacaxi", "Contem cpf");
            return;
        }
        else{
            mensagem.setValue("Preencha os dados corretamente.");
            Log.d("Abacaxi", "Error");
            return;
        }
    }
}
