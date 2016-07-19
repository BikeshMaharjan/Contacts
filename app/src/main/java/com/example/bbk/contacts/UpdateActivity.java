package com.example.bbk.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    EditText edtFirstname,edtLastname,edtMobileno,edtHomeno,edtWorkno;
    Button btnUpdate,btnCancel;

    Database db;

    public static String Mobile;
    public static String Work;
    public static String Home;
    public static String Firstname;
    public static String Lastname;

    public String[] userdata;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtFirstname= (EditText) findViewById(R.id.edtFirstNameId);
        edtLastname= (EditText) findViewById(R.id.edtLastNameId);
        edtMobileno= (EditText) findViewById(R.id.edtMobileNoId);
        edtWorkno= (EditText) findViewById(R.id.edtOfficeNoId);
        edtHomeno= (EditText) findViewById(R.id.edtHomeNoId);
        btnUpdate= (Button) findViewById(R.id.btnDoneId);
        btnCancel= (Button) findViewById(R.id.btnCancelId);

        db = new Database(UpdateActivity.this);
         i = getIntent();
       final int updateid= i.getIntExtra("updateid",0);

        fetchSpecificData(updateid);

        edtFirstname.setText(userdata[0]);
        edtLastname.setText(userdata[1]);
        edtMobileno.setText(userdata[2]);
        edtWorkno.setText(userdata[3]);
        edtHomeno.setText(userdata[4]);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String newfirstname=   edtFirstname.getText().toString();
              String newlastname=  edtLastname.getText().toString();
             String newmobile=   edtMobileno.getText().toString();
              String newworkno=  edtWorkno.getText().toString();
              String newhomeno=  edtHomeno.getText().toString();

                updateContact(updateid,newfirstname,newlastname,newmobile,newworkno,newhomeno);

            }
        });
    }

    public void fetchSpecificData(int id)
    {
        db.open();
        Cursor result = db.fetchSpecificContactFromDatabase(id);
        result.moveToFirst();
        do{
            Firstname=result.getString(1);
            Lastname=result.getString(2);
            Mobile=result.getString(3);
          //  Log.d("MobileNumber: ", Mobile);

            Work = result.getString(4);
           // Log.d("WorkNumber : ",Work);

            Home = result.getString(5);
          //  Log.d("HomeNumber : ",Home);

           userdata = new String[]{Firstname,Lastname,Mobile,Work,Home};

        }while(result.moveToNext());
        result.close();
        db.close();
    }

    public void updateContact(int id, String firstname, String lastname, String mobile, String work, String home){
        db.open();
        db.updateContact(id, firstname, lastname, mobile, work, home);
        db.close();
        setResult(RESULT_OK,i);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update, menu);
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
}
