package com.yordy.ecoresi.customValidation;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

/**
 * Created by yordy on 02/02/2017.
 */
public class PasswordValidation extends Validator {
    public static String passw;
    public PasswordValidation(String customErrorMessage) {
        super(customErrorMessage);

    }

    @Override
    public boolean isValid(EditText et) {
        Log.e(et.getText().toString(),passw);
        if(et.getText().length()<8){
            this.errorMessage = "Debe de tener al menos 8 caracteres.";
            return false;
        }else if(!TextUtils.equals(et.getText(), passw)){
            this.errorMessage = "Las contraseñas no coinciden.";
            return false;
        }
        return true;
    }

    public static void pass(String pass){
        passw = pass;
    }
}
