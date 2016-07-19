package com.example.bbk.contacts.com.example.bbk.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bbk.contacts.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by BBK on 02/25/16.
 */
public class Adapter extends BaseAdapter
{
    Context context;
    LayoutInflater inflater;
    List<RankingDataModel> rankingDataModels = new ArrayList<>();

    public Adapter(Context context,List<RankingDataModel> rankingDataModel){
        this.context=context;
       this.rankingDataModels=rankingDataModel;
        inflater=LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return rankingDataModels.size();
    }

    @Override
    public RankingDataModel getItem(int position) {
        return rankingDataModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyView myviewholder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.activity_descriptionpage,parent,false);

            myviewholder = new MyView(convertView);
            convertView.setTag(myviewholder);
        }
        else{
            myviewholder= (MyView) convertView.getTag();
        }

        RankingDataModel rankingDataModel=getItem(position);

        myviewholder.phonetype.setText(rankingDataModel.getPhonetype());
        myviewholder.phonenumber.setText(rankingDataModel.getPhonenumber());
        myviewholder.phonelogo.setImageResource(rankingDataModel.getPhoneimage());

        return convertView;

    }

    public class MyView{
        TextView phonetype, phonenumber;
        ImageView phonelogo;
        public MyView(View item){
            phonetype= (TextView) item.findViewById(R.id.txtPhoneTypeId);
            phonenumber= (TextView) item.findViewById(R.id.txtNumberId);
            phonelogo= (ImageView) item.findViewById(R.id.imgDisplayId);
        }

    }
}
