package com.example.yournotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes = new ArrayList<>();
     static  ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;
    ListView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sharedPreferences = getApplicationContext().getSharedPreferences("Your notes", Context.MODE_PRIVATE);
        load();

        gridView =findViewById(R.id.listview);
//        ArrayList<String> hashSet = (ArrayList<String>) sharedPreferences.getString("notes",null)
//        if (hashSet== null){
//            notes.add("Sample Note");n
//
//        }else
//        {
//            notes= new ArrayList<>(hashSet);
//        }




        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        gridView.setAdapter(arrayAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Noteeditor.class);
                intent.putExtra("noteid",i);
                startActivity(intent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemtodelete = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete this notes")
                        .setMessage("This notes will be permanently deleted and cannot be recovered")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemtodelete);
                                arrayAdapter.notifyDataSetChanged();

//                                HashSet<String> hashSet = new HashSet<>(MainActivity.notes);
//                                sharedPreferences.edit().putString("notes", String.valueOf(hashSet)).apply();

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),Noteeditor.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "This feature is not available yet", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

        save();
    }

    private void save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("notes list size" , notes.size());

        for (int i =0 ; i<notes.size() ; i++){

            editor.putString("notes"+i , notes.get(i));

        }

        editor.apply();
    }
    public void load(){

        ArrayList<String> updatedList = new ArrayList<>();

        int listSize = sharedPreferences.getInt("notes list size" , 0);

        for(int i=0 ; i<listSize ; i++){

            String note = sharedPreferences.getString("notes"+i , null);

            updatedList.add(note);

        }

        notes = updatedList;

    }
}
