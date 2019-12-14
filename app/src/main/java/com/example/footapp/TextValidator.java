package com.example.footapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.List;

public abstract class TextValidator implements TextWatcher {
    private final TextView textView;
    private final int validation;
    private List<String> gamesNames;
    private CreateGame createGame;

    final static public int emptyChecker = 0;
    final static public int gameNameValid = 1;
    final static public int numPlayersValid = 2;

    final static private int minNumOfPlayers = 6;
    final static private int maxNumOfPlayers = 6;
    final static private String numOfPlayersMsg = "Number of players may only contain numbers";
    final static private String numOfPlayersMinMaxMsg = "Number of players must be between " + minNumOfPlayers + " and " + maxNumOfPlayers;
    final static private String numOfPlayersIsMsg = "Number of players in beta must be " + minNumOfPlayers;
    final static private String gameNameMsg = "A game named %s already exists. choose a unique name";
    final static private String emptyFieldMsg = "Required field";

    public TextValidator(TextView textView, int validation, CreateGame createGame) {
        this.textView = textView;
        this.validation = validation;
        this.createGame = createGame;
        this.gamesNames = this.createGame.getGamesNames();
    }

    private void validate(TextView textView, String text)
    {
        validateEmptyField(textView, text);

        switch(validation)
        {
            case(numPlayersValid):
                validateNumPlayers(textView, text);
                break;

            case(gameNameValid):
                validateGameName(textView, text);
                break;
        }
    }

    @Override
    final public void afterTextChanged(Editable s) {
        createGame.setCreateButtonActiveOrNot();
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

    private void validateGameName(TextView textView, String text)
    {
        if (text.equals("")) {
            return;
        }
        for (int i = 0; i < gamesNames.size(); ++i) {
            if (text.equals(gamesNames.get(i)))
            {
                textView.setError(String.format(gameNameMsg, text));
            }
        }
    }

    private void validateEmptyField(TextView textView, String text)
    {
        if (text.trim().equals("")) {
            textView.setError(String.format(emptyFieldMsg));
        }
    }
}