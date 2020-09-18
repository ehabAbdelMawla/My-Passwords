package com.example.mypasswords.Threads;

import android.app.Activity;

import com.example.mypasswords.DataBase.DB;
import com.example.mypasswords.ActivitiesControllers.MainActivity;
import com.example.mypasswords.Models.Site;

public class DBThread  extends Thread{

public static long result=0;
private Site site;
private String action;
private Activity temp;
    public DBThread(Site s,String Action,Activity temp){
        this.action=Action;
        this.site=s;
        this.temp=temp;
        DBThread.result=0;
    }
    @Override
    public void run(){
        DB db=new DB(temp);
                        if(action.equalsIgnoreCase("add")){
                    long result=db.addNewSite(this.site);
                    DBThread.result=result;
                }else{
                    this.site.setId(MainActivity.tempSite.getId());
                    long result=db.updateSite(this.site);
                    DBThread.result=result;
                }
    }



}

