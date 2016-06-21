package com.example.japf.coursejun16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button byeButton;
    Button greButton;

    TextView textView;
    TextView textPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textView = (TextView) findViewById(R.id.textView);
        byeButton = (Button) findViewById(R.id.bye_button);
        byeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textView.setText("Bye");
            }
        });
        greButton = (Button) findViewById(R.id.greet_button);
        greButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textView.setText("Hello World!");
            }
        });

        textPref = (TextView) findViewById(R.id.textPreference);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        textPref.setText(sharedPref.getBoolean("pref_show_capital", false)?"Show Cap":"Not Capital");
    }

    @Override
    public void onStart(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        textPref.setText(sharedPref.getBoolean("pref_show_capital", false)?"Show Cap...":"Not Capital...");
        super.onStart();
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
