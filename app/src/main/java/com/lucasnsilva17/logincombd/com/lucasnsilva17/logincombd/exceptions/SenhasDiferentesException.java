package com.lucasnsilva17.logincombd.com.lucasnsilva17.logincombd.exceptions;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class SenhasDiferentesException extends Exception implements Serializable {

    private String message;

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
