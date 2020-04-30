package com.example.mypasswords;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class getDataThread extends Thread{
    public static  customeArrayAddapter<Site> itemsAdapter;
    private Activity currentActivity;
    private ListView myListView;
    public static ArrayList<Site> myData;

public  getDataThread(Activity currentActivity,ListView myListView){
    this.currentActivity=currentActivity;
    this.myListView=myListView;
    ArrayList<Site> myData=null;
    itemsAdapter=null;
}

    @Override
    public void run(){
        DB db=new DB(currentActivity);
         myData=db.getAllSites();
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.tempSite=myData.get(position);
                Intent addNewPasswordPage=new Intent(currentActivity,AddnewSiteData.class);
                currentActivity.startActivity(addNewPasswordPage);
            }
        });
        itemsAdapter=new customeArrayAddapter<Site>(currentActivity,R.layout.list_item,myData);
//        myListView.setAdapter(itemsAdapter);
    }
}
