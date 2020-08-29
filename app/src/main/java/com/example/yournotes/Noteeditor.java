package com.example.yournotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class Noteeditor extends AppCompatActivity {
    int noteid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteeditor);
        EditText editText =findViewById(R.id.editnotes);
        Intent intent = getIntent();
         noteid = intent.getIntExtra("noteid",-1);
        if (noteid != -1)
        {
            editText.setText(MainActivity.notes.get(noteid));
        }
        else {
            MainActivity.notes.add(" ");
            noteid =MainActivity.notes.size()-1;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.notes.set(noteid,String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Your notes", Context.MODE_PRIVATE);
                HashSet<String> hashSet = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putString("notes", String.valueOf(hashSet)).apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
