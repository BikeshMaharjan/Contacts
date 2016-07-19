package com.example.bbk.contacts;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bbk.contacts.com.example.bbk.custom.Adapter;
import com.example.bbk.contacts.com.example.bbk.custom.RankingDataModel;

import java.util.ArrayList;
import java.util.List;

public class CustomContactListActivity extends AppCompatActivity {
    ListView listView;
    List<RankingDataModel> list = new ArrayList<>();

    Database db;

    public static String Mobile="mobile";
    public static String Work ="work";
    public static String Home="home";
    public static String Firstname="firstname";
    public static String Lastname = "lastname";

    public String[] phonetype= new String[]{"MOBILE","WORK","HOME"};
    int[] phoneimage= new int[]{R.mipmap.ic_mobile,R.mipmap.ic_office,R.mipmap.ic_home};
    public String[] phonenumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_contact_list);


        listView= (ListView) findViewById(R.id.customlistviewId);

        db=new Database(CustomContactListActivity.this);


        Intent i = getIntent();
        int id=i.getIntExtra("Id", 0);

        fetchSpecificData(id);
        GetElementInList();

        getSupportActionBar().setTitle(Firstname+" "+ Lastname);


        Adapter adapter=new Adapter(CustomContactListActivity.this,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RankingDataModel pos=list.get(position);
                String phonenumber=pos.getPhonenumber();
                Log.d("phonenumber=",phonenumber);

               /* Intent intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:"+phonenumber));

                startActivity(intent);*/


               Intent callintent = new Intent();
                callintent.setAction(Intent.ACTION_CALL);

                callintent.setData(Uri.parse("tel: " + phonenumber));

                if (callintent.resolveActivity(getPackageManager())!=null){
                    startActivity(callintent);
                }
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
            Log.d("MobileNumber: ", Mobile);

            Work = result.getString(4);
            Log.d("WorkNumber : ",Work);

            Home = result.getString(5);
            Log.d("HomeNumber : ",Home);

            phonenumbers = new String[]{Mobile,Work,Home};

        }while(result.moveToNext());
        result.close();
        db.close();
    }

    private void GetElementInList() {
        for (int i=0;i<phonetype.length;i++){
            RankingDataModel rankingDataModel=new RankingDataModel();

            rankingDataModel.setPhoneimage(phoneimage[i]);
            rankingDataModel.setPhonetype(phonetype[i]);
            rankingDataModel.setPhonenumber(phonenumbers[i]);

            list.add(rankingDataModel);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_contact_list, menu);
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
