package com.example.prj_bankapp.util;

import android.content.Context;
import android.widget.Toast;

public class ToolBox {

    public static void displayMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
