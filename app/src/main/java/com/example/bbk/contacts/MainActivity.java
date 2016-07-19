package com.example.bbk.contacts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String USERNAME = "default_user";
    private static final String PASSWORD = "default_password";
    private static final int REQUEST_CODE = 1 ;
    EditText edtUsername,edtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtPassword= (EditText) findViewById(R.id.passwordId);
        edtUsername= (EditText) findViewById(R.id.usernameId);
        btnLogin= (Button) findViewById(R.id.btnLoginId);



        SharedPreferences sharedPreferences = getSharedPreferences("logincredentials", MODE_PRIVATE);
        String chkusername = sharedPreferences.getString(USERNAME, "nouser");
        String chkpassword = sharedPreferences.getString(PASSWORD, "nopassword");
        if (chkusername!="nouser" && chkpassword!="nopassword") {

            Intent intent = new Intent(MainActivity.this, Contacts.class);

            startActivityForResult(intent, REQUEST_CODE);

            Toast.makeText(getApplicationContext(),"You are logged in.",Toast.LENGTH_LONG).show();
        }





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                writeSharedPreferenceValues(username,password);

                Intent intent = new Intent(MainActivity.this, Contacts.class);

                startActivityForResult(intent, REQUEST_CODE);

                Toast.makeText(getApplicationContext(),"You are logged in.",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void writeSharedPreferenceValues(String username, String password) {
        SharedPreferences sharedPreferences=getSharedPreferences(getString(R.string.preferenceKeyvalue), MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);

        editor.commit();

    }

  /*  private void readSharedPreferenceValues(String username, String password){
        SharedPreferences sharedPreferences=getSharedPreferences("logincredentials",MODE_PRIVATE);
        String chkusername= sharedPreferences.getString(USERNAME, username);
        String chkpassword= sharedPreferences.getString(PASSWORD, password);
    }*/



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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE){
            if (resultCode==RESULT_OK){
                Toast.makeText(MainActivity.this, "Logout sucessfull", Toast.LENGTH_SHORT).show();
             //   finish();
            }
        }
    }
}
