package com.example.mypasswords.Threads;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mypasswords.ActivitiesControllers.AddnewSiteData;
import com.example.mypasswords.DataBase.DB;
import com.example.mypasswords.ActivitiesControllers.MainActivity;
import com.example.mypasswords.Models.Site;
import com.example.mypasswords.R;
import com.example.mypasswords.ActivitiesControllers.customeArrayAddapter;

import java.util.ArrayList;

import static android.widget.AbsListView.CHOICE_MODE_SINGLE;

public class getDataThread extends Thread {
    public static customeArrayAddapter<Site> itemsAdapter;
    private Activity currentActivity;
    private ListView myListView;
    public static ArrayList<Site> myData;

    public getDataThread(Activity currentActivity, ListView myListView) {
        this.currentActivity = currentActivity;
        this.myListView = myListView;
        ArrayList<Site> myData = null;
        itemsAdapter = null;
    }

    @Override
    public void run() {
        DB db = new DB(currentActivity);
        myData = db.getAllSites();
        myListView.setChoiceMode(CHOICE_MODE_SINGLE);
        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ClipboardManager clipboard = (ClipboardManager) currentActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", myData.get(position).getPass());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(currentActivity.getApplicationContext(), "Password Copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.tempSite = myData.get(position);
                Intent addNewPasswordPage = new Intent(currentActivity, AddnewSiteData.class);
                currentActivity.startActivity(addNewPasswordPage);
            }
        });

        itemsAdapter = new customeArrayAddapter<Site>(currentActivity, R.layout.list_item, myData);

    }
}
