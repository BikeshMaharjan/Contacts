package com.example.bbk.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddcontactActivity extends AppCompatActivity {

    EditText edtFirstname,edtLastname,edtMobileno,edtHomeno,edtWorkno;
    Button btnDone;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);

        //constructor in database class
        db= new Database(getApplicationContext());

        edtFirstname= (EditText) findViewById(R.id.edtFirstNameId);
        edtLastname= (EditText) findViewById(R.id.edtLastNameId);
        edtMobileno= (EditText) findViewById(R.id.edtMobileNoId);
        edtWorkno= (EditText) findViewById(R.id.edtOfficeNoId);
        edtHomeno= (EditText) findViewById(R.id.edtHomeNoId);
        btnDone= (Button) findViewById(R.id.btnDoneId);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtFirstname = edtFirstname.getText().toString();
                String txtLastname = edtLastname.getText().toString();
                String txtMobileno= edtMobileno.getText().toString();
                String txtWorkno= edtWorkno.getText().toString();
                String txtHomeno=edtHomeno.getText().toString();
               // Toast.makeText(getApplicationContext(), txtFirstname+"", Toast.LENGTH_SHORT).show();
                /* insert function*/
               InsertDataIntoDatabase(txtFirstname, txtLastname, txtMobileno, txtWorkno, txtHomeno);
               // InsertDataIntoDatabase("Bikesh","Maharjan","9843423594","9849001615","9802074324");
            }
        });
    }

    private void InsertDataIntoDatabase(String txtFirstname, String txtLastname, String txtMobileno, String txtWorkno, String txtHomeno) {

      //  Toast.makeText(getApplicationContext(), txtFirstname+"", Toast.LENGTH_SHORT).show();
        db.open();
        long result=  db.insertContacts(txtFirstname, txtLastname, txtMobileno, txtWorkno, txtHomeno);
        db.close();
       // Toast.makeText(getApplicationContext(), result + "", Toast.LENGTH_LONG).show();
        if (result>0){
           Intent intent=getIntent();
            intent.putExtra("datarow",result);
            setResult(RESULT_OK,intent);
            Toast.makeText(getApplicationContext(), "Contact Added.", Toast.LENGTH_LONG).show();
            finish();
           // Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_SHORT).show();
        }
       else{
            Toast.makeText(getApplicationContext(), "Unsuccessfull", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addcontact, menu);
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
