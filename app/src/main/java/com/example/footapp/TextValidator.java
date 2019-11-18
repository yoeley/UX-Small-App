package com.example.footapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/*
use case for example: ("location" is a name of an editText box)

location.addTextChangedListener(new TextValidator(location) {
            @Override public void validate(TextView textView, String text) {
                Pattern p = Pattern.compile( "[0-9]" );
                Matcher m = p.matcher(text);
                if (m.find())
                {
                    textView.setError("location may not contain numbers");
                }
            }
        });
 */

public abstract class TextValidator implements TextWatcher {
    private final TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}