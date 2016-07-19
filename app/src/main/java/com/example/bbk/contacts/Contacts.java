package com.example.bbk.contacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Contacts extends AppCompatActivity {
    ListView listView;
    Database db;

    public static int ID= 0;
    public static String Firstname= "fname";
    public static String Lastname="lname";


  //  public List<String> ContactNames= new ArrayList<>();
    public List<Integer> idList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        listView= (ListView) findViewById(R.id.listviewId);

        db = new Database(this);

        fetchContacts();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              /*  List<String>  specificcontact =fetchSpecificContact(id);
                String mobile=specificcontact[0];*/
                Intent intent = new Intent(Contacts.this, CustomContactListActivity.class);
                intent.putExtra("Id", idList.get(position));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final CharSequence[] options = {"Delete", "Edit"};
                final int dataid = idList.get(position);
                // Log.d("dataid",dataid+"");
                final AlertDialog.Builder alertdialog = new AlertDialog.Builder(Contacts.this);
                alertdialog.setTitle("Contact Options");
                alertdialog.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which].equals("Delete")) {
                            alertdialog.setTitle("Delete");
                            alertdialog.setMessage("Item will be deleted");
                            alertdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Log.d("deleteidinonclick",dataid+"");
                                    DeleteContactByUser(dataid);
                                }
                            });
                            alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_SHORT).show();
                                }
                            });
                            AlertDialog deletedialog = alertdialog.create();
                            deletedialog.show();
                        } else if (options[which].equals("Edit")) {
                            Intent intent = new Intent(Contacts.this, UpdateActivity.class);
                            intent.putExtra("updateid", dataid);
                            startActivityForResult(intent, 3);

                        }

                    }
                });
                AlertDialog dialog = alertdialog.create();
                dialog.show();

                return true;
            }
        });

      }

    private void DeleteContactByUser(int deleteid) {
        db.open();
        db.deleteContact(deleteid);
        db.close();
        Toast.makeText(getApplicationContext(), "Delete Succcessful", Toast.LENGTH_SHORT).show();
        adapter.clear();
        fetchContacts();
    }



    // function to fetch all the data and display firstname and last name in contact page
    public List<String> fetchContacts(){

        db.open();

       List<String> ContactNames= new ArrayList<>();
        idList= new ArrayList<>();

        Cursor result = db.fetchAllContacts();
        if (result.getCount()>0)
        {
            for (int i=0;i<result.getCount();i++){
                result.moveToNext();
                ID=result.getInt(0);
                Firstname = result.getString(1);
                Lastname=result.getString(2);

                ContactNames.add(Firstname+ " "+ Lastname);
                idList.add(ID);
                //Log.d("firstname=", Firstname);

            }
        }
        adapter= new ArrayAdapter<String>(Contacts.this,android.R.layout.simple_list_item_1,ContactNames);
        listView.setAdapter(adapter);

        result.close();
        db.close();
        return ContactNames;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
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

    //logout button function to logout session
    public void logout(MenuItem item) {
        SharedPreferences sharedPreferences=getSharedPreferences(getString(R.string.preferenceKeyvalue), Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();


        Intent intent=getIntent();
        setResult(RESULT_OK,intent);
        finish();
    }

    //navigating to another activity to add contact
    public void addcontact(MenuItem item) {
        Intent intent=new Intent(Contacts.this,AddcontactActivity.class);

        startActivityForResult(intent, 2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            if(resultCode==RESULT_OK){
               adapter.clear();
                fetchContacts();
                Toast.makeText(getApplicationContext(), "Contact Added.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode==3){
            if (resultCode==RESULT_OK){
                adapter.clear();
                fetchContacts();
                Toast.makeText(getApplicationContext(),"Contact Updated",Toast.LENGTH_SHORT).show();
            }
        }

    }

}
