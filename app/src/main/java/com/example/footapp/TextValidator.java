package com.example.footapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public abstract class TextValidator implements TextWatcher {
    private final TextView textView;
    private final int validation;


    final static public int numPlayersValid = 0;


    final static private int minNumOfPlayers = 6;
    final static private int maxNumOfPlayers = 6;
    final static private String numOfPlayersMsg = "Number of players may only contain numbers";
    final static private String numOfPlayersMinMaxMsg = "Number of players must be between " + minNumOfPlayers + " and " + maxNumOfPlayers;
    final static private String numOfPlayersIsMsg = "Number of players in beta must be " + minNumOfPlayers;

    public TextValidator(TextView textView, int validation) {
        this.textView = textView;
        this.validation = validation;
    }

    private void validate(TextView textView, String text)
    {
        switch(validation)
        {
            case(numPlayersValid):
                validateNumPlayers(textView, text);
                break;
        }
    }

    @Override
    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }

    private void validateNumPlayers(TextView textView, String text)
    {
        String number_regex = "\\d*";
        if(!text.matches(number_regex))
        {
            textView.setError(numOfPlayersMsg);
        }
        else
        {
            int input;
            try
            {
                input = Integer.parseInt(text);
            }
            catch (NumberFormatException e)
            {
                textView.setError(numOfPlayersMsg);
                return;
            }
            if(input < minNumOfPlayers || input > maxNumOfPlayers)
            {
                if(minNumOfPlayers == maxNumOfPlayers)
                {
                    textView.setError(numOfPlayersIsMsg);
                }
                else
                {
                    textView.setError(numOfPlayersMinMaxMsg);
                }
            }
        }
    }
}