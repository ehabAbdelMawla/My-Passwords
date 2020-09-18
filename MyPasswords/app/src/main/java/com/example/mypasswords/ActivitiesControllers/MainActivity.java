package com.example.mypasswords.ActivitiesControllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;

import com.example.mypasswords.DataBase.DB;
import com.example.mypasswords.Models.Site;
import com.example.mypasswords.R;
import com.example.mypasswords.Threads.getDataThread;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static DB dbdd;

    public static int currentTheme;
    public static Site tempSite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbdd=new DB(this);
        currentTheme =dbdd.getCurrentTheme().equalsIgnoreCase("light")? R.style.lightTheme:R.style.darkTheme;
        setTheme(currentTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("MainActivity","onCreate Fire");

    }

    @Override
    protected void onStart() {
       final Switch switchTheme=findViewById(R.id.switch1);
        switchTheme.setChecked(dbdd.getCurrentTheme().equalsIgnoreCase("dark"));
        switchTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchTheme.isChecked()){
                    dbdd.changeTheme("dark");
                }else{
                    dbdd.changeTheme("light");
                }
                recreate();
            }
        });


        super.onStart();

        FloatingActionButton addBtn= findViewById(R.id.AddNewPasswordButton);
       final MainActivity thisView=(MainActivity)this;
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                                tempSite=null;
                Intent addNewPasswordPage=new Intent(thisView,AddnewSiteData.class);
                startActivity(addNewPasswordPage);

            }
        });

    DB db=new DB(this);
    final ArrayList<Site> myData=db.getAllSites();
        ListView rootView= findViewById(R.id.rootView);
        getDataThread s=new getDataThread(thisView,rootView);
        s.start();


    while(getDataThread.itemsAdapter==null){

    }


        if(getDataThread.myData.size()==0){
            View empty = getLayoutInflater().inflate(R.layout.empty, null, false);
            addContentView(empty, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            rootView.setEmptyView(empty);
        }

        rootView.setAdapter(getDataThread.itemsAdapter);

    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
